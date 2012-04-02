package com.polymathiccoder.averroes.meta.validation.rule.model;

import static ch.lambdaj.Lambda.convert;

import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import ch.lambdaj.function.convert.Converter;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.polymathiccoder.averroes.meta.validation.rule.DroolsSyntaxHelper;

@AutoProperty
public class AccompaniedWithAtLeastOneTemplateModel {
	private final String annotationTypeSimpleName;
	private final List<String> otherAnnotationTypesSimpleNameList;
	
	public AccompaniedWithAtLeastOneTemplateModel(String annotationTypeSimpleName, List<String> otherAnnotationTypesSimpleNameList) {
		this.annotationTypeSimpleName = annotationTypeSimpleName;
		this.otherAnnotationTypesSimpleNameList = Lists.newArrayList(otherAnnotationTypesSimpleNameList);
	}

	public String getAnnotationTypeSimpleName() {
		return annotationTypeSimpleName;
	}
	
	public String getOtherAnnotationTypesSimpleNameList () {
		return Joiner.on(", ").join(convert(otherAnnotationTypesSimpleNameList,
			new Converter<String, String>() {
				public String convert(String otherAnnotationTypeSimpleName) {
					return "@" + otherAnnotationTypeSimpleName;
				}
			}));
	}
	
	public String getOtherAnnotationTypesExpression () {
		return DroolsSyntaxHelper.buildExpression(otherAnnotationTypesSimpleNameList, "not contains", "&&");
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
