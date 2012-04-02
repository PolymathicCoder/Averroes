package com.polymathiccoder.averroes.meta.validation;

import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;

import com.google.common.collect.Lists;
import com.polymathiccoder.averroes.meta.model.AnnotatedElement;
import com.polymathiccoder.averroes.meta.validation.rule.DroolsRuleCodeGenerator;

public class AnnotatedElementValidator {	
	private static final StatelessKnowledgeSession DROOLS_KNOWLEDGE_SESSION;
	
	static {
		KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();  
		builder.add(ResourceFactory.newFileResource(
			DroolsRuleCodeGenerator.AVERROES_DROOLS_VALIDATION_GENERATED_FILE.getAbsoluteFile()), 
			ResourceType.DRL);  
		
		if (builder.hasErrors()) {  
			throw new RuntimeException(builder.getErrors().toString());  
		}  

		KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();  
		knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());  
		DROOLS_KNOWLEDGE_SESSION = knowledgeBase.newStatelessKnowledgeSession();  
	}
	
	public ValidationResult validate(AnnotatedElement annotatedElement) {
		List<?> errors = Lists.newArrayList();
		List<?> warnings = Lists.newArrayList();
		DROOLS_KNOWLEDGE_SESSION.setGlobal("errors", errors);
		DROOLS_KNOWLEDGE_SESSION.setGlobal("warnings", warnings);

		DROOLS_KNOWLEDGE_SESSION.execute(annotatedElement);
		
		return new ValidationResult(errors, warnings);
	}
}
