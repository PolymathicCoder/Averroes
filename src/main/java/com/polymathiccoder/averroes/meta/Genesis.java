package com.polymathiccoder.averroes.meta;

import java.lang.annotation.Annotation;

import com.polymathiccoder.averroes.meta.model.MetaModelCodeGenerator;
import com.polymathiccoder.averroes.meta.processing.AnnotatedElementProcessorStrategy;
import com.polymathiccoder.averroes.meta.processing.MetaProcessingCodeGenerator;

public class Genesis {
	public static AnnotatedElementProcessorStrategy letThereBeAProcessorStrategy(Class<? extends Annotation> annotationType) {
		AnnotatedElementProcessorStrategy processorStrategy = null;
		MetaModelCodeGenerator.generateAndLoadConcreteDecoratorClassForAnnotationType(annotationType);			
		Class<?> concreteAnnotatedElementProcessorStrategy = MetaProcessingCodeGenerator.generateAndLoadConcreteAnnotatedElementProcessorStrategyForAnnotation(annotationType);
		try {
			processorStrategy = (AnnotatedElementProcessorStrategy) concreteAnnotatedElementProcessorStrategy.newInstance();
		} catch (InstantiationException instantiationException) {
			//TODO Handle better
		} catch (IllegalAccessException illegalAccessException) {
			//TODO Handle better
		}
		return processorStrategy;
	}
	
	
}
