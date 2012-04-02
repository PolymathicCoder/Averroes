package com.polymathiccoder.averroes.meta.processing;

import java.util.Set;

import com.polymathiccoder.averroes.meta.model.AnnotatedElement;
import com.polymathiccoder.averroes.meta.model.ComplexAnnotatedElement;

public class AnnotatedElementMetaProcessorVisitor implements AnnotatedElementVisitor {
	private Set<AnnotatedElementProcessorStrategy> processorStrategies;
	
	public AnnotatedElementMetaProcessorVisitor(Set<AnnotatedElementProcessorStrategy> processorStrategies) {
		this.processorStrategies = processorStrategies;
	}
	
	private void process(AnnotatedElement annotatedElement) {
		for(AnnotatedElementProcessorStrategy processorStrategy : processorStrategies) {
			processorStrategy.execute(annotatedElement);	
		}
	}

	public void visit(AnnotatedElement elementToVisit) {
		process(elementToVisit);
	}
	
	public void visit(ComplexAnnotatedElement complexAnnotatedElementToVisit) {
		process(complexAnnotatedElementToVisit);
	}
	
	public Set<AnnotatedElementProcessorStrategy> getProcessorStrategies() {
		return processorStrategies;
	}
}
