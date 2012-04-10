package com.polymathiccoder.averroes.meta.validation.processing;

import java.util.List;
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

import com.google.common.collect.Lists;
import com.polymathiccoder.averroes.meta.validation.annotation.AccompaniedWithAtLeastOne;
import com.polymathiccoder.averroes.meta.validation.rule.DroolsRuleCodeGenerator;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("com.polymathiccoder.averroes.meta.validation.annotation.AccompaniedWithAtLeastOne")
public class AccompaniedWithAtLeastOneProcessor extends AbstractProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccompaniedWithAtLeastOneProcessor.class);
	
	private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }
    
	@Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
		LOGGER.info("Processing @AccompaniedWithAtLeastOne: Creating Drools validation rules");
		
		String annotationTypeSimpleName = StringUtils.EMPTY;
		
		Set<? extends Element> rootElements = roundEnvironment.getRootElements();
		for (Element element : rootElements) {
			if (element.getAnnotation(AccompaniedWithAtLeastOne.class) == null) {
				continue;
			}
			
			List<String> otherAnnotationTypesSimpleNameList = Lists.newArrayList();
			
			annotationTypeSimpleName = element.getSimpleName().toString();

			Element accompaniedWithAtLeastOneElement = elementUtils.getTypeElement(AccompaniedWithAtLeastOne.class.getName());
						
			AnnotationValue accompaniedWithAtLeastOneValueAttribute = null;
			
			for (AnnotationMirror accompaniedWithAtLeastOneAnnotationMirror : elementUtils.getAllAnnotationMirrors(element)) {
	            if (accompaniedWithAtLeastOneAnnotationMirror.getAnnotationType().equals(accompaniedWithAtLeastOneElement.asType())) {	            	
	            	for(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementUtils.getElementValuesWithDefaults(accompaniedWithAtLeastOneAnnotationMirror).entrySet()) {
	                	if("value".equals(entry.getKey().getSimpleName().toString())) {
	                		accompaniedWithAtLeastOneValueAttribute = entry.getValue();
	                    }
	                }
	            }
	        }
			
			@SuppressWarnings("unchecked")
			List<? extends AnnotationValue> annotationValues = (List<? extends AnnotationValue>) accompaniedWithAtLeastOneValueAttribute.getValue();
			for(AnnotationValue annotationValue : annotationValues) {
				DeclaredType declaredType = (DeclaredType) annotationValue.getValue();
				otherAnnotationTypesSimpleNameList.add(declaredType.asElement().getSimpleName().toString());
			}
						
			DroolsRuleCodeGenerator.generateForAccompaniedWithAtLeastOne(annotationTypeSimpleName, otherAnnotationTypesSimpleNameList);
		}

		return true;
	}
}
