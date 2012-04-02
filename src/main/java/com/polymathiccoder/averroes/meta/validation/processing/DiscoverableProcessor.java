package com.polymathiccoder.averroes.meta.validation.processing;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.polymathiccoder.averroes.meta.validation.rule.DroolsRuleCodeGenerator;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("com.polymathiccoder.averroes.meta.validation.annotation.Discoverable")
public class DiscoverableProcessor extends AbstractProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(DiscoverableProcessor.class);

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
		LOGGER.info("Processing @Discoverable: Discovering Averroes validation annotations");

		Elements elementUtils = processingEnv.getElementUtils();

		Set<? extends Element> rootElements = roundEnvironment.getRootElements();
		for (Element element : rootElements) {
			if (element.getKind() != ElementKind.PACKAGE) {
				continue;
			}
			
			PackageElement packageElement = elementUtils.getPackageOf(element);
			String packageName = packageElement.getQualifiedName().toString();
			
			DroolsRuleCodeGenerator.generateHeader(packageName);
			DroolsRuleCodeGenerator.generateCommons();
		}
		return true;
	}
}