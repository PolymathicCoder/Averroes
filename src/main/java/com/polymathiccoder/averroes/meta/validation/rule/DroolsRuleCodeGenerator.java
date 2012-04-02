package com.polymathiccoder.averroes.meta.validation.rule;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.drools.template.ObjectDataCompiler;

import com.polymathiccoder.averroes.meta.validation.rule.model.AccompaniedWithAtLeastOneTemplateModel;
import com.polymathiccoder.averroes.meta.validation.rule.model.AccompaniedWithTemplateModel;
import com.polymathiccoder.averroes.meta.validation.rule.model.ForTypeAnnotatedWithTemplateModel;
import com.polymathiccoder.averroes.meta.validation.rule.model.ForTypeIncludingTemplateModel;
import com.polymathiccoder.averroes.meta.validation.rule.model.ForTypeTemplateModel;
import com.polymathiccoder.averroes.meta.validation.rule.model.NotAccompaniedWithTemplateModel;

public class DroolsRuleCodeGenerator {
	public final static File AVERROES_DROOLS_VALIDATION_GENERATED_FILE = new File(DroolsRuleCodeGenerator.class.getResource("/META-INF").getFile() + "/averroes-validation.drl");
	
	private final static String DROOLS_GENERATED_RULES_PACKAGE_NAME = "package com.polymathiccoder.averroes.meta.validation.generated\n\n";
	
	private final static String DROOLS_TEMPLATE_FILE_LOCATION_FOR__COMMONS = "/META-INF/rules/commons.drl";
	private final static String DROOLS_TEMPLATE_FILE_LOCATION_FOR__NOT_ACCOMPANIED_WITH = "/META-INF/rules/templates/notAccompaniedWith.drl";
	private final static String DROOLS_TEMPLATE_FILE_LOCATION_FOR__ACCOMPANIED_WITH = "/META-INF/rules/templates/accompaniedWith.drl";
	private final static String DROOLS_TEMPLATE_FILE_LOCATION_FOR__ACCOMPANIED_WITH_AT_LEAST_ONE = "/META-INF/rules/templates/accompaniedWithAtLeastOne.drl";
	private final static String DROOLS_TEMPLATE_FILE_LOCATION_FOR__FOR_TYPE = "/META-INF/rules/templates/forType.drl";
	private final static String DROOLS_TEMPLATE_FILE_LOCATION_FOR__FOR_TYPE_INCLUDING_EXACTLY = "/META-INF/rules/templates/forTypeIncludingExactly.drl";
	private final static String DROOLS_TEMPLATE_FILE_LOCATION_FOR__FOR_TYPE_INCLUDING_AT_LEAST_AT_MOST = "/META-INF/rules/templates/forTypeIncludingAtLeastAtMost.drl";
	private final static String DROOLS_TEMPLATE_FILE_LOCATION_FOR__FOR_TYPE_ANNOTATED_WITH = "/META-INF/rules/templates/forTypeAnnotatedWith.drl";
	
	public static void generateHeader(String targetPackageName) {
		if (AVERROES_DROOLS_VALIDATION_GENERATED_FILE.exists()) {
			FileUtils.deleteQuietly(AVERROES_DROOLS_VALIDATION_GENERATED_FILE);
		}
		
		StringBuilder builder = new StringBuilder()
			.append(DROOLS_GENERATED_RULES_PACKAGE_NAME)
			.append("import ")
			.append(targetPackageName)
			.append(".*;\n\n");
		
		try {
			FileUtils.writeStringToFile(AVERROES_DROOLS_VALIDATION_GENERATED_FILE, builder.toString());
		} catch (IOException ioException) {
			//This will never happen... I will always be there for you...
		}
	}
	
	public static void generateCommons() {
		StringBuilder builder = new StringBuilder();
		try {
			URL url = new URL("jar:"+DroolsRuleCodeGenerator.class.getResource(DROOLS_TEMPLATE_FILE_LOCATION_FOR__COMMONS).getFile());
			builder.append(IOUtils.toString(url.openStream()));

			FileUtils.writeStringToFile(AVERROES_DROOLS_VALIDATION_GENERATED_FILE, builder.toString(), true);
		} catch (IOException ioException) {
			ioException.printStackTrace();
			//This will never happen... I will always be there for you...
		}
	}

	public static void generateForNotAccompaniedWith(String annotationTypeSimpleName, List<String> otherAnnotationTypesSimpleNameList) {
		Collection<NotAccompaniedWithTemplateModel> paramSets = new ArrayList<NotAccompaniedWithTemplateModel>();
		paramSets.add(new NotAccompaniedWithTemplateModel(annotationTypeSimpleName, otherAnnotationTypesSimpleNameList));
		
		ObjectDataCompiler converter = new ObjectDataCompiler();
		InputStream templateStream = DroolsRuleCodeGenerator.class.getResourceAsStream(DROOLS_TEMPLATE_FILE_LOCATION_FOR__NOT_ACCOMPANIED_WITH);
		
		String rule = StringUtils.remove(converter.compile(paramSets, templateStream), DROOLS_GENERATED_RULES_PACKAGE_NAME);
		
		try {
			FileUtils.writeStringToFile(AVERROES_DROOLS_VALIDATION_GENERATED_FILE, rule, true);
		} catch (IOException ioException) {
			//This will never happen... I will always be there for you...
		}
	}
	
	public static void generateForAccompaniedWith(String annotationTypeSimpleName, List<String> otherAnnotationTypesSimpleNameList) {
		Collection<AccompaniedWithTemplateModel> paramSets = new ArrayList<AccompaniedWithTemplateModel>();
		paramSets.add(new AccompaniedWithTemplateModel(annotationTypeSimpleName, otherAnnotationTypesSimpleNameList));
		
		ObjectDataCompiler converter = new ObjectDataCompiler();
		InputStream templateStream = DroolsRuleCodeGenerator.class.getResourceAsStream(DROOLS_TEMPLATE_FILE_LOCATION_FOR__ACCOMPANIED_WITH);
		
		String rule = StringUtils.remove(converter.compile(paramSets, templateStream), DROOLS_GENERATED_RULES_PACKAGE_NAME);
		
		try {
			FileUtils.writeStringToFile(AVERROES_DROOLS_VALIDATION_GENERATED_FILE, rule, true);
		} catch (IOException ioException) {
			//This will never happen... I will always be there for you...
		}
	}
	
	public static void generateForAccompaniedWithAtLeastOne(String annotationTypeSimpleName, List<String> otherAnnotationTypesSimpleNameList) {
		Collection<AccompaniedWithAtLeastOneTemplateModel> paramSets = new ArrayList<AccompaniedWithAtLeastOneTemplateModel>();
		paramSets.add(new AccompaniedWithAtLeastOneTemplateModel(annotationTypeSimpleName, otherAnnotationTypesSimpleNameList));
		
		ObjectDataCompiler converter = new ObjectDataCompiler();
		InputStream templateStream = DroolsRuleCodeGenerator.class.getResourceAsStream(DROOLS_TEMPLATE_FILE_LOCATION_FOR__ACCOMPANIED_WITH_AT_LEAST_ONE);
		
		String rule = StringUtils.remove(converter.compile(paramSets, templateStream), DROOLS_GENERATED_RULES_PACKAGE_NAME);
		
		try {
			FileUtils.writeStringToFile(AVERROES_DROOLS_VALIDATION_GENERATED_FILE, rule, true);
		} catch (IOException ioException) {
			//This will never happen... I will always be there for you...
		}
	}
	
	public static void generateForForType(String annotationTypeSimpleName, List<String> typesSimpleNameList) {
		Collection<ForTypeTemplateModel> paramSets = new ArrayList<ForTypeTemplateModel>();
		paramSets.add(new ForTypeTemplateModel(annotationTypeSimpleName, typesSimpleNameList));
		
		ObjectDataCompiler converter = new ObjectDataCompiler();
		InputStream templateStream = DroolsRuleCodeGenerator.class.getResourceAsStream(DROOLS_TEMPLATE_FILE_LOCATION_FOR__FOR_TYPE);
		
		String rule = StringUtils.remove(converter.compile(paramSets, templateStream), DROOLS_GENERATED_RULES_PACKAGE_NAME);
		
		try {
			FileUtils.writeStringToFile(AVERROES_DROOLS_VALIDATION_GENERATED_FILE, rule, true);
		} catch (IOException ioException) {
			//This will never happen... I will always be there for you...
		}
	}
	
	public static void generateForForTypeIncludingExactly(String annotationTypeSimpleName, String includedAnnotationTypeSimpleName, String exactly, String atLeast, String atMost) {
		Collection<ForTypeIncludingTemplateModel> paramSets = new ArrayList<ForTypeIncludingTemplateModel>();
		paramSets.add(new ForTypeIncludingTemplateModel(annotationTypeSimpleName, includedAnnotationTypeSimpleName, exactly, atLeast, atMost));
		
		ObjectDataCompiler converter = new ObjectDataCompiler();
		
		InputStream templateStream;
		templateStream = DroolsRuleCodeGenerator.class.getResourceAsStream(DROOLS_TEMPLATE_FILE_LOCATION_FOR__FOR_TYPE_INCLUDING_EXACTLY);
		
		String rule = StringUtils.remove(converter.compile(paramSets, templateStream), DROOLS_GENERATED_RULES_PACKAGE_NAME);
		
		try {
			FileUtils.writeStringToFile(AVERROES_DROOLS_VALIDATION_GENERATED_FILE, rule, true);
		} catch (IOException ioException) {
			//This will never happen... I will always be there for you...
		}
	}
	
	public static void generateForForTypeIncludingAtLeastAtMost(String annotationTypeSimpleName, String includedAnnotationTypeSimpleName, String exactly, String atLeast, String atMost) {
		Collection<ForTypeIncludingTemplateModel> paramSets = new ArrayList<ForTypeIncludingTemplateModel>();
		paramSets.add(new ForTypeIncludingTemplateModel(annotationTypeSimpleName, includedAnnotationTypeSimpleName, exactly, atLeast, atMost));
		
		ObjectDataCompiler converter = new ObjectDataCompiler();
		
		InputStream templateStream;
		templateStream = DroolsRuleCodeGenerator.class.getResourceAsStream(DROOLS_TEMPLATE_FILE_LOCATION_FOR__FOR_TYPE_INCLUDING_AT_LEAST_AT_MOST);
		
		String rule = StringUtils.remove(converter.compile(paramSets, templateStream), DROOLS_GENERATED_RULES_PACKAGE_NAME);
		
		try {
			FileUtils.writeStringToFile(AVERROES_DROOLS_VALIDATION_GENERATED_FILE, rule, true);
		} catch (IOException ioException) {
			//This will never happen... I will always be there for you...
		}
	}
	
	public static void generateForForTypeAnnotatedWith(String annotationTypeSimpleName, List<String> annotatedTypeAnnotationTypesSimpleNameList) {
		Collection<ForTypeAnnotatedWithTemplateModel> paramSets = new ArrayList<ForTypeAnnotatedWithTemplateModel>();
		
		for (String annotatedTypeAnnotationTypeSimpleName : annotatedTypeAnnotationTypesSimpleNameList) {
			paramSets.add(new ForTypeAnnotatedWithTemplateModel(annotationTypeSimpleName, annotatedTypeAnnotationTypeSimpleName));
		}
		
		ObjectDataCompiler converter = new ObjectDataCompiler();
		InputStream templateStream = DroolsRuleCodeGenerator.class.getResourceAsStream(DROOLS_TEMPLATE_FILE_LOCATION_FOR__FOR_TYPE_ANNOTATED_WITH);
		
		String rule = StringUtils.remove(converter.compile(paramSets, templateStream), DROOLS_GENERATED_RULES_PACKAGE_NAME);
		
		try {
			FileUtils.writeStringToFile(AVERROES_DROOLS_VALIDATION_GENERATED_FILE, rule, true);
		} catch (IOException ioException) {
			//This will never happen... I will always be there for you...
		}
	}
}
