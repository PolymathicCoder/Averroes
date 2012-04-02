package com.polymathiccoder.averroes.meta.model;

import java.lang.reflect.Field;
import java.util.Set;

import com.google.common.collect.Sets;

public class AnnotatedClass extends ComplexAnnotatedElement {			
	private static Set<AnnotatedField> getAnnotatedFields(Class<?> clazz) {
		Set<AnnotatedField> annotatedFields = Sets.newHashSet();
		for(Field field : clazz.getDeclaredFields()) {
			annotatedFields.add(new AnnotatedField(field));
		}
		return annotatedFields;
	}
	
	public AnnotatedClass(Class<?> clazz) {
		super(clazz, clazz.getSimpleName(), getAnnotatedFields(clazz));
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("--> Class: ");		
		builder.append(super.toString());
		
		return builder.toString();
	}
}
