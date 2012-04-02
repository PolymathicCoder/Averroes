package com.polymathiccoder.averroes.meta.processing;

import com.polymathiccoder.averroes.meta.model.AnnotatedElement;
import com.polymathiccoder.averroes.meta.model.ComplexAnnotatedElement;

public interface AnnotatedElementVisitor {
	void visit(AnnotatedElement elementToVisit);
	void visit(ComplexAnnotatedElement complexAnnotatedElementToVisit);
}
