package com.polymathiccoder.averroes.meta.validation.processing;

import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.polymathiccoder.averroes.meta.validation.annotation.ForTypeIncluding;
import com.polymathiccoder.averroes.meta.validation.rule.DroolsRuleCodeGenerator;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("com.polymathiccoder.averroes.meta.validation.annotation.ForTypeIncluding")
public class ForTypeIncludingProcessor extends AbstractProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(ForTypeIncludingProcessor.class);

    private Elements elementUtils;

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }
    
	@Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
		LOGGER.info("Processing @ForTypeIncluding: Creating Drools validation rules");

		String annotationTypeSimpleName = StringUtils.EMPTY, includedAnnotationTypeSimpleName = StringUtils.EMPTY;
		Integer exactly, atLeast, atMost;
		
		Set<? extends Element> rootElements = roundEnvironment.getRootElements();
		for (Element element : rootElements) {
			if (element.getAnnotation(ForTypeIncluding.class) == null) {
				continue;
			}
			
			annotationTypeSimpleName = element.getSimpleName().toString();

			Element forTypeIncludingElement = elementUtils.getTypeElement(ForTypeIncluding.class.getName());
						
			AnnotationValue forTypeIncludingValueAttribute = null, forTypeIncludingExactlyAttribute = null, forTypeIncludingAtLeastAttribute = null, forTypeIncludingAtMostAttribute = null;
			
			for (AnnotationMirror forTypeIncludingAnnotationMirror : elementUtils.getAllAnnotationMirrors(element)) {
	            if (forTypeIncludingAnnotationMirror.getAnnotationType().equals(forTypeIncludingElement.asType())) {	            	
	            	for(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementUtils.getElementValuesWithDefaults(forTypeIncludingAnnotationMirror).entrySet()) {
	                	if("value".equals(entry.getKey().getSimpleName().toString())) {
	                        forTypeIncludingValueAttribute = entry.getValue();
	                    } else if("exactly".equals(entry.getKey().getSimpleName().toString())) {
	                    	forTypeIncludingExactlyAttribute = entry.getValue();
	                    } else if("atLeast".equals(entry.getKey().getSimpleName().toString())) {
	                    	forTypeIncludingAtLeastAttribute = entry.getValue();
	                    } else if("atMost".equals(entry.getKey().getSimpleName().toString())) {
	                    	forTypeIncludingAtMostAttribute = entry.getValue();
	                    }
	                }
	            }
	        }
			
			DeclaredType declaredTypeValueAttribute = (DeclaredType) forTypeIncludingValueAttribute.getValue();
			includedAnnotationTypeSimpleName = declaredTypeValueAttribute.asElement().getSimpleName().toString();
			
			exactly = (Integer) forTypeIncludingExactlyAttribute.getValue();
			atLeast = (Integer) forTypeIncludingAtLeastAttribute.getValue();
			atMost = (Integer) forTypeIncludingAtMostAttribute.getValue();
			
			if (atLeast != 0 || atMost != Integer.MAX_VALUE) {
				DroolsRuleCodeGenerator.generateForForTypeIncludingAtLeastAtMost(annotationTypeSimpleName, includedAnnotationTypeSimpleName, exactly.toString(), atLeast.toString(), atMost.toString());
			} else {
				DroolsRuleCodeGenerator.generateForForTypeIncludingExactly(annotationTypeSimpleName, includedAnnotationTypeSimpleName, exactly.toString(), atLeast.toString(), atMost.toString());
			}
		}

		return false;
	}
}
