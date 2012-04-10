package com.polymathiccoder.averroes.meta.processing;

import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import javassist.Modifier;
import javassist.NotFoundException;

import com.polymathiccoder.averroes.meta.model.AnnotatedDecorator;

public class MetaProcessingCodeGenerator {
	private final static VelocityEngine VELOCITY_ENGINE;
	static {
		VELOCITY_ENGINE = new VelocityEngine();
		Properties props = new Properties();
	    props.put("resource.loader", "class");
	    props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    props.put("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
	    VELOCITY_ENGINE.init(props);
	}
	
	//Concrete Annotated Element Processor Strategy Generator
	@SuppressWarnings("unchecked")
	public static Class<? extends AnnotatedDecorator> generateAndLoadConcreteAnnotatedElementProcessorStrategyForAnnotation(Class<? extends Annotation> annotationType) {
		String generatedClassName = "AnnotatedElement" + annotationType.getSimpleName() + "ProcessorStrategy";
		
		ClassPool classPool = ClassPool.getDefault();
		classPool.appendClassPath(new LoaderClassPath(MetaProcessingCodeGenerator.class.getClassLoader()));
		classPool.importPackage("java.lang.reflect");
		classPool.importPackage("com.polymathiccoder.averroes.meta.model");
		classPool.importPackage(annotationType.getPackage().getName());
		
		Class<? extends AnnotatedDecorator> clazz = null;
		try {
			CtClass ctClass = classPool.getOrNull(generatedClassName);
			if (ctClass == null) {
				ctClass = classPool.makeClass(generatedClassName);
			
				CtClass strategyInterface = classPool.get("com.polymathiccoder.averroes.meta.processing.AnnotatedElementProcessorStrategy");
				ctClass.setInterfaces(new CtClass[] {strategyInterface});
				ctClass.setModifiers(Modifier.PUBLIC);
	
				CtMethod ctMethod = CtNewMethod.make(generateConcreteAnnotatedElementProcessorStrategyExecuteMethodForAnnotation(annotationType), ctClass);
				ctMethod.setModifiers(Modifier.PUBLIC);
				ctClass.addMethod(ctMethod);
				
				clazz = ctClass.toClass();
			}
			
			clazz = (Class<? extends AnnotatedDecorator>) Class.forName(generatedClassName);
		} catch (NotFoundException notFoundException) {
			//TODO Handle better
		} catch (CannotCompileException cannotCompileException) {
			//TODO Handle better
		} catch (ClassNotFoundException classNotFoundException) {
			//TODO Handle better
		}
		
		return clazz;
	}
	
	private static String generateConcreteAnnotatedElementProcessorStrategyExecuteMethodForAnnotation(Class<? extends Annotation> annotationType) {		
		Template template = VELOCITY_ENGINE.getTemplate("META-INF/concreteAnnotatedElementProcessorStrategyExecuteMethod.vm");
        VelocityContext context = new VelocityContext();
        context.put("annotation", annotationType);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
	}
}
