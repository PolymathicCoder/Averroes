package com.polymathiccoder.averroes.meta.model;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.polymathiccoder.averroes.meta.processing.AnnotatedElementVisitor;

public class ComplexAnnotatedElement extends AnnotatedElement {
	private Set<? extends AnnotatedElement> annotatedElements;

	public ComplexAnnotatedElement(java.lang.reflect.AnnotatedElement annotatedElement, String propertyNavigationPath, Set<? extends AnnotatedElement> annotatedElements) {
		super(annotatedElement, propertyNavigationPath);
		this.annotatedElements = Sets.newHashSet(annotatedElements);

		for(AnnotatedElement element : annotatedElements) {
			element.setPropertyNavigationPath(propertyNavigationPath + NAVIGATION_PATH_SEPARATOR + element.getNavigationPath());
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

	public Metamodel getMetaModel() {
		//TODO Must include elements own metatadata
		Map<String, Object> metadata = Maps.newHashMap();
		for(AnnotatedElement annotatedElement : getAnnotatedElements()) {
			metadata.putAll(annotatedElement.getMetaModel().getMetadata());
		}
		return new Metamodel(metadata);
	}
}
