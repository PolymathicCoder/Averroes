package com.polymathiccoder.averroes.meta.validation.rule;

import static ch.lambdaj.Lambda.convert;
import static ch.lambdaj.Lambda.join;

import java.util.List;

import ch.lambdaj.function.convert.Converter;

public class DroolsSyntaxHelper {
	public static String buildExpression(final List<String> classesSimpleNames, final String prefixKeyword, final String infixLogicalOperator) {
		StringBuilder builder = new StringBuilder("(")
			.append(
				join(convert(classesSimpleNames,
					new Converter<String, String>() {
						public String convert(String classSimpleName) {
							return prefixKeyword + " " + classSimpleName + ".class";
						}
					}
				), " " + infixLogicalOperator + " ")
			)
			.append(")");
		return builder.toString();
	}
}
