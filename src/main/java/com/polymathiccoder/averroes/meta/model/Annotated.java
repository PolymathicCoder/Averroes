package com.polymathiccoder.averroes.meta.model;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

public interface Annotated {
	String getNavigationPath();
	Multiset<Class<? extends Annotation>> getAnnotationTypes();
	Multimap<Class<? extends Annotation>, Pair<String, ? extends Object>> getAnnotationTypesAndData();
	Metamodel getMetaModel();
}
