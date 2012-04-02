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
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.polymathiccoder.averroes.meta.validation.annotation.ForType;
import com.polymathiccoder.averroes.meta.validation.rule.DroolsRuleCodeGenerator;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("com.polymathiccoder.averroes.meta.validation.annotation.ForType")
public class ForTypeProcessor extends AbstractProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(ForTypeProcessor.class);

	private Elements elementUtils;
	private Types typeUtils;

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
    }
    
	@Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
		LOGGER.info("Processing @ForType: Creating Drools validation rules");

		String annotationTypeSimpleName = StringUtils.EMPTY;
		
		Set<? extends Element> rootElements = roundEnvironment.getRootElements();
		for (Element element : rootElements) {
			if (element.getAnnotation(ForType.class) == null) {
				continue;
			}
			
			List<String> otherAnnotationTypesSimpleNameList = Lists.newArrayList();
			
			annotationTypeSimpleName = element.getSimpleName().toString();

			Element forTypeElement = elementUtils.getTypeElement(ForType.class.getName());
						
			AnnotationValue forTypeValueAttribute = null;
			
			for (AnnotationMirror forTypeAnnotationMirror : elementUtils.getAllAnnotationMirrors(element)) {
	            if (forTypeAnnotationMirror.getAnnotationType().equals(forTypeElement.asType())) {	            	
	            	for(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementUtils.getElementValuesWithDefaults(forTypeAnnotationMirror).entrySet()) {
	                	if("value".equals(entry.getKey().getSimpleName().toString())) {
	                		forTypeValueAttribute = entry.getValue();
	                    }
	                }
	            }
	        }
			
			@SuppressWarnings("unchecked")
			List<? extends AnnotationValue> annotationValues = (List<? extends AnnotationValue>) forTypeValueAttribute.getValue();

			for(AnnotationValue annotationValue : annotationValues) {
				TypeMirror typeMirror = (TypeMirror) annotationValue.getValue();
				if (typeMirror.getKind() == TypeKind.ARRAY) {
					otherAnnotationTypesSimpleNameList.add(((ArrayType) typeMirror).toString());
				} else if (typeMirror.getKind().isPrimitive()) {
					otherAnnotationTypesSimpleNameList.add(((PrimitiveType) typeMirror).toString());
				} else {
					otherAnnotationTypesSimpleNameList.add(typeUtils.asElement(typeMirror).getSimpleName().toString());
				}
			}
						
			DroolsRuleCodeGenerator.generateForForType(annotationTypeSimpleName, otherAnnotationTypesSimpleNameList);
		}

		return false;
	}
}
