package com.polymathiccoder.averroes.meta.model;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.polymathiccoder.averroes.meta.processing.AnnotatedElementToVisit;
import com.polymathiccoder.averroes.meta.processing.AnnotatedElementVisitor;

public class AnnotatedElement implements Annotated, AnnotatedElementToVisit {
	protected final static String NAVIGATION_PATH_SEPARATOR = ".";
	protected final static String NO_DATA = "N/A";

	private String navigationPath;
	private final java.lang.reflect.AnnotatedElement annotatedElement;
	private final Multimap<Class<? extends Annotation>, Pair<String, ? extends Object>> annotationTypesAndData;

	public AnnotatedElement(java.lang.reflect.AnnotatedElement annotatedElement, String navigationPath) {
		this.navigationPath = navigationPath;
		this.annotatedElement = annotatedElement;
		annotationTypesAndData = HashMultimap.create();
	}

	public String getNavigationPath() {
		return navigationPath;
	}

	public void setPropertyNavigationPath(String propertyNavigationPath) {
		this.navigationPath = propertyNavigationPath;
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
		builder.append("\nNavigationPath: ");
		builder.append(getNavigationPath());
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

	public Metamodel getMetaModel() {
		Map<String, Object> metadata = Maps.newHashMap();
		for (Entry<Class<? extends Annotation>, Collection<Pair<String, ? extends Object>>> entry : annotationTypesAndData.asMap().entrySet()) {
			String fullNavigationPath = navigationPath + NAVIGATION_PATH_SEPARATOR + WordUtils.uncapitalize(ClassUtils.getShortClassName(entry.getKey()));
			for (Pair<String, ? extends Object> data : entry.getValue()) {
				if (data == null) {
					metadata.put(fullNavigationPath, NO_DATA);
				} else {
					metadata.put(fullNavigationPath + NAVIGATION_PATH_SEPARATOR + data.getLeft(), data.getRight());
				}
			}
		}

		return new Metamodel(metadata);
	}
}
