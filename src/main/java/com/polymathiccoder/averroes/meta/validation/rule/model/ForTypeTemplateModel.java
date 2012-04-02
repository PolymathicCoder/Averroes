package com.polymathiccoder.averroes.meta.validation.rule.model;

import static ch.lambdaj.Lambda.convert;

import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import ch.lambdaj.function.convert.Converter;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

@AutoProperty
public class ForTypeTemplateModel {
	private final String annotationTypeSimpleName;
	private final List<String> typesSimpleNameList;
	
	public ForTypeTemplateModel(String annotationTypeSimpleName, List<String> typesSimpleNameList) {
		this.annotationTypeSimpleName = annotationTypeSimpleName;
		this.typesSimpleNameList = Lists.newArrayList(typesSimpleNameList);
	}

	public String getAnnotationTypeSimpleName() {
		return annotationTypeSimpleName;
	}
	
	public String getTypesSimpleNameList () {
		return Joiner.on(", ").join(typesSimpleNameList);
	}
	
	public String getTypesParametersList () {
		return Joiner.on(", ").join(convert(typesSimpleNameList,
			new Converter<String, String>() {
				public String convert(String classSimpleName) {
					return classSimpleName + ".class";
				}
			}));
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
