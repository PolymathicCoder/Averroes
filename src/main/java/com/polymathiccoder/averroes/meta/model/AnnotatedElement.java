package com.polymathiccoder.averroes.meta.model;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.polymathiccoder.averroes.meta.processing.AnnotatedElementToVisit;
import com.polymathiccoder.averroes.meta.processing.AnnotatedElementVisitor;

public class AnnotatedElement implements Annotated, AnnotatedElementToVisit {
	private String propertyNavigationPath;
	private final java.lang.reflect.AnnotatedElement annotatedElement;
	private final Multimap<Class<? extends Annotation>, Pair<String, ? extends Object>> annotationTypesAndData;

	public AnnotatedElement(java.lang.reflect.AnnotatedElement annotatedElement, String propertyNavigationPath) {
		this.propertyNavigationPath = propertyNavigationPath;
		this.annotatedElement = annotatedElement;
		annotationTypesAndData = HashMultimap.create();
	}
	
	public String getPropertyNavigationPath() {
		return propertyNavigationPath;
	}
	
	public void setPropertyNavigationPath(String propertyNavigationPath) {
		this.propertyNavigationPath = propertyNavigationPath;
	}
	
	public Multiset<Class<? extends Annotation>> getAnnotationTypes() {
		return annotationTypesAndData.keys();
	}

	public Multimap<Class<? extends Annotation>, Pair<String, ? extends Object>> getAnnotationTypesAndData() {
		return annotationTypesAndData;
	}

	public java.lang.reflect.AnnotatedElement getAnnotatedElement() {
		return annotatedElement;
	}
	
	public void accept(AnnotatedElementVisitor visitor) {
		visitor.visit(this);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nPropertyNavigationPath: ");
		builder.append(getPropertyNavigationPath());
		builder.append("\n");
		builder.append(getAnnotatedElement());
		builder.append("\n");
		builder.append("Annotated with: ");
		builder.append(getAnnotationTypes());
		builder.append("\n");
		builder.append("Whose data:     ");
		builder.append(getAnnotationTypesAndData());
		
		return builder.toString();
	}

	public Map<String, Multimap<Class<? extends Annotation>, Pair<String, ? extends Object>>> getMetaModel() {
		Map<String, Multimap<Class<? extends Annotation>, Pair<String, ? extends Object>>> metaModel = new HashMap<String, Multimap<Class<? extends Annotation>,Pair<String,? extends Object>>>();
		metaModel.put(propertyNavigationPath, annotationTypesAndData);
		return metaModel;
	}
}
