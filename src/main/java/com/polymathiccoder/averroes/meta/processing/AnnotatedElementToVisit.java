package com.polymathiccoder.averroes.meta.processing;

public interface AnnotatedElementToVisit {
	void accept(AnnotatedElementVisitor visitor);
}
