package com.polymathiccoder.averroes.meta.util;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class AnnotationDiscoverer {
	public static Set<Class<? extends Annotation>> discoverAnnotationsOfType(String targetPackage, Class<? extends Annotation> annotationType) {
		Reflections reflections = new Reflections(targetPackage, new TypeAnnotationsScanner());
		return new HashSet<Class<? extends Annotation>>(reflections.getSubTypesOf(annotationType));
	}
	
	public static Set<Class<?>> discoverAnnotatedWith(String targetPackage, Class<? extends Annotation> annotationType) {
		Reflections reflections = new Reflections(targetPackage, new TypeAnnotationsScanner());
		return new HashSet<Class<?>>(reflections.getTypesAnnotatedWith(annotationType));
	}
	
	public static Set<Class<?>> discoverAnnotatedWith(Class<? extends Annotation> annotationType) {
		Reflections reflections = new Reflections(
			new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forJavaClassPath())
				.setScanners(new TypeAnnotationsScanner()));
		return new HashSet<Class<?>>(reflections.getTypesAnnotatedWith(annotationType));
	}
}
