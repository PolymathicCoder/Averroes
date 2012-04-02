package com.polymathiccoder.averroes.meta.validation.rule.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class ForTypeAnnotatedWithTemplateModel {
	private final String annotationTypeSimpleName;
	private final String annotatedTypeAnnotationTypeSimpleName;
	
	public ForTypeAnnotatedWithTemplateModel(String annotationTypeSimpleName, String annotatedTypeAnnotationTypeSimpleName) {
		this.annotationTypeSimpleName = annotationTypeSimpleName;
		this.annotatedTypeAnnotationTypeSimpleName = annotatedTypeAnnotationTypeSimpleName;
	}

	public String getAnnotationTypeSimpleName() {
		return annotationTypeSimpleName;
	}

	public String getAnnotatedTypeAnnotationTypeSimpleName() {
		return annotatedTypeAnnotationTypeSimpleName;
	}

	@Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }

    @Override
    public boolean equals(final Object other) {
        return Pojomatic.equals(this, other);
    }
}
