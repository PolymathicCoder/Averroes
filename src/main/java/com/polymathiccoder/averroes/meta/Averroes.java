package com.polymathiccoder.averroes.meta;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Sets;
import com.polymathiccoder.averroes.meta.model.AnnotatedClass;
import com.polymathiccoder.averroes.meta.model.AnnotatedElement;
import com.polymathiccoder.averroes.meta.model.AnnotatedField;
import com.polymathiccoder.averroes.meta.model.Metamodel;
import com.polymathiccoder.averroes.meta.processing.AnnotatedElementMetaProcessorVisitor;
import com.polymathiccoder.averroes.meta.processing.AnnotatedElementProcessorStrategy;
import com.polymathiccoder.averroes.meta.processing.AnnotatedElementVisitor;
import com.polymathiccoder.averroes.meta.util.AnnotationDiscoverer;
import com.polymathiccoder.averroes.meta.validation.AnnotatedElementValidator;
import com.polymathiccoder.averroes.meta.validation.ValidationResult;
import com.polymathiccoder.averroes.meta.validation.annotation.Discoverable;

public class Averroes {
	private Set<AnnotatedElementProcessorStrategy> processorStrategies;
	private AnnotatedElementVisitor visitor;
	private AnnotatedElementValidator validator;

	public Averroes() {
		processorStrategies = Sets.newHashSet();
		processDiscoverablePackages();
	}

	private void processDiscoverablePackages() {
		Set<Class<?>> annotatedPackages = AnnotationDiscoverer.discoverAnnotatedWith(Discoverable.class);
    	Iterator<Class<?>> it = annotatedPackages.iterator();
    	while (it.hasNext()) {
    		Package annotatedPackage = it.next().getPackage();
    		processDiscoverablePackage(annotatedPackage.getName());
    	}
	}

	private void processDiscoverablePackage(String annotationPackage) {
		for (Class<? extends Annotation> annotationType : AnnotationDiscoverer.discoverAnnotationsOfType(annotationPackage, Annotation.class)) {
			processorStrategies.add(Genesis.letThereBeAProcessorStrategy(annotationType));
		}

		visitor = new AnnotatedElementMetaProcessorVisitor(processorStrategies);
        validator = new AnnotatedElementValidator();
	}

	public Metamodel constructMetamodelFor(java.lang.reflect.AnnotatedElement element) {
		try {
			return cache.get(element).getMetaModel();
		} catch (ExecutionException executionException) {
			//TODO Handle better
		}
		return null;
	}

	private LoadingCache<java.lang.reflect.AnnotatedElement, AnnotatedElement> cache = CacheBuilder
		.newBuilder()
		.concurrencyLevel(4)
		.weakKeys()
		.maximumSize(1000)
		.build(new CacheLoader<java.lang.reflect.AnnotatedElement, AnnotatedElement>() {
			public AnnotatedElement load(java.lang.reflect.AnnotatedElement key) {
				return goCrazy(key);
			}
		});

	private AnnotatedElement goCrazy(java.lang.reflect.AnnotatedElement element) {
		AnnotatedElement annotatedElement = null;
		if (element instanceof Class<?>) {
			annotatedElement = new AnnotatedClass((Class<?>) element);
		} else if (element instanceof Field) {
			annotatedElement = new AnnotatedField((Field) element);
		} else {
			//TODO throw an exception for not supported
		}
		annotatedElement.accept(visitor);

		ValidationResult validationResult = validator.validate(annotatedElement);
		if (validationResult.getErrors().size() > 0){
			//TODO throw an exception
			//throw new RuntimeException(validationResult.getErrors().toString());
			System.out.println(validationResult.getErrors());
			System.exit(0);
		}

		if (validationResult.getWarnings().size() > 0){
			//TODO throw an exception
		}

		return annotatedElement;
	}
}
