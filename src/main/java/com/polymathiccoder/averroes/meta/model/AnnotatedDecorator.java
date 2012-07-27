package com.polymathiccoder.averroes.meta.model;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

public abstract class AnnotatedDecorator implements Annotated {
	protected Annotated decoratedAnnotated;

	public AnnotatedDecorator(Annotated annotated) {
		this.decoratedAnnotated = annotated;
	}

	public String getPropertyNavigationPath() {
		return decoratedAnnotated.getNavigationPath();
	}

	public Multiset<Class<? extends Annotation>> getAnnotationTypes() {
		return decoratedAnnotated.getAnnotationTypes();
	}

	public Multimap<Class<? extends Annotation>, Pair<String, ? extends Object>> getAnnotationTypesAndData() {
		return decoratedAnnotated.getAnnotationTypesAndData();
	}

	public Metamodel getMetaModel() {
		return decoratedAnnotated.getMetaModel();
	}
}
