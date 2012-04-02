package com.polymathiccoder.averroes.meta.model;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

public interface Annotated {
	String getPropertyNavigationPath();
	Multiset<Class<? extends Annotation>> getAnnotationTypes();
	Multimap<Class<? extends Annotation>, Pair<String, ? extends Object>> getAnnotationTypesAndData();
	Map<String, Multimap<Class<? extends Annotation>, Pair<String, ? extends Object>>> getMetaModel();
}
