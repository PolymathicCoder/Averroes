package com.polymathiccoder.averroes.meta.validation.rule.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class ForTypeIncludingTemplateModel {
	private final String annotationTypeSimpleName;
	private final String includedAnnotationTypeSimpleName;
	private final String exactly;
	private final String atLeast;
	private final String atMost;
	
	public ForTypeIncludingTemplateModel(String annotationTypeSimpleName, String includedAnnotationTypeSimpleName, String exactly, String atLeast, String atMost) {
		this.annotationTypeSimpleName = annotationTypeSimpleName;
		this.includedAnnotationTypeSimpleName = includedAnnotationTypeSimpleName;
		this.exactly = exactly;
		this.atLeast = atLeast;
		this.atMost = atMost;
	}

	public String getAnnotationTypeSimpleName() {
		return annotationTypeSimpleName;
	}
	
	public String getIncludedAnnotationTypeSimpleName() {
		return includedAnnotationTypeSimpleName;
	}
	
	public String getExactly() {
		return exactly;
	}
	
	public String getAtLeast() {
		return atLeast;
	}
	
	public String getAtMost() {
		return atMost;
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
