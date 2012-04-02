package com.polymathiccoder.averroes.meta.model;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.polymathiccoder.averroes.meta.processing.AnnotatedElementVisitor;

public class ComplexAnnotatedElement extends AnnotatedElement {
	private Set<? extends AnnotatedElement> annotatedElements;
	
	public ComplexAnnotatedElement(java.lang.reflect.AnnotatedElement annotatedElement, String propertyNavigationPath, Set<? extends AnnotatedElement> annotatedElements) {
		super(annotatedElement, propertyNavigationPath);
		this.annotatedElements = Sets.newHashSet(annotatedElements);
		
		for(AnnotatedElement element : annotatedElements) {
			element.setPropertyNavigationPath(propertyNavigationPath + "." + element.getPropertyNavigationPath());
		}
	}

	public Set<? extends AnnotatedElement> getAnnotatedElements() {
		return annotatedElements;
	}
	
	public void accept(AnnotatedElementVisitor visitor) {
		for(AnnotatedElement annotatedElement : annotatedElements) {
			annotatedElement.accept(visitor);
		}
		visitor.visit(this);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(super.toString());
		builder.append("\n\t---> Children:\n");
		
		for(AnnotatedElement annotatedElement : getAnnotatedElements()) {
			builder.append("\t");
			builder.append(annotatedElement);
			builder.append("\n");
		}
		
		return builder.toString();
	}
	
	public Map<String, Multimap<Class<? extends Annotation>, Pair<String, ? extends Object>>> getMetaModel() {
		Map<String, Multimap<Class<? extends Annotation>, Pair<String, ? extends Object>>> metaModel = new HashMap<String, Multimap<Class<? extends Annotation>, Pair<String, ? extends Object>>>();
		for(AnnotatedElement annotatedElement : getAnnotatedElements()) {
			metaModel.put(annotatedElement.getPropertyNavigationPath(), annotatedElement.getAnnotationTypesAndData());
		}
		return metaModel;
	}
}
