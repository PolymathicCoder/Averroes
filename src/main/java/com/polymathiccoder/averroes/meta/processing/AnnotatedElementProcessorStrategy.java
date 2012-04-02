package com.polymathiccoder.averroes.meta.processing;

import com.polymathiccoder.averroes.meta.model.Annotated;
import com.polymathiccoder.averroes.meta.model.AnnotatedElement;

public interface AnnotatedElementProcessorStrategy {
	Annotated execute(AnnotatedElement annotatedElement);
}
