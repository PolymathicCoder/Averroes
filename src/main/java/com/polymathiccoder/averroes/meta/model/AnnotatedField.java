package com.polymathiccoder.averroes.meta.model;

import java.lang.reflect.Field;

public class AnnotatedField extends AnnotatedElement {	
	public AnnotatedField(Field field) {
		super(field, field.getName());
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("--> Field: ");
		builder.append(super.toString());
		
		return builder.toString();
	}
}
