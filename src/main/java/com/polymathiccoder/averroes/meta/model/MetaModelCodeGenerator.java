package com.polymathiccoder.averroes.meta.model;

import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtNewConstructor;
import javassist.LoaderClassPath;
import javassist.Modifier;
import javassist.NotFoundException;

public class MetaModelCodeGenerator {
	private final static VelocityEngine VELOCITY_ENGINE;
	static {
		VELOCITY_ENGINE = new VelocityEngine();
		Properties props = new Properties();
	    props.put("resource.loader", "class");
	    props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    props.put("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
	    VELOCITY_ENGINE.init(props);
	}
	
	//Concrete Decorator Class Generator
	@SuppressWarnings("unchecked")
	public static Class<? extends AnnotatedDecorator> generateAndLoadConcreteDecoratorClassForAnnotationType(Class<? extends Annotation> annotationType) {
		String generatedClassName = "AnnotatedWith" + annotationType.getSimpleName();
		
		ClassPool classPool = ClassPool.getDefault();
		classPool.appendClassPath(new LoaderClassPath(MetaModelCodeGenerator.class.getClassLoader()));
		classPool.importPackage("com.polymathiccoder.averroes.meta.model");
		classPool.importPackage(annotationType.getPackage().getName());
		
		Class<? extends AnnotatedDecorator> clazz = null;
		try {			
			CtClass ctClass = classPool.getOrNull(generatedClassName);
			if (ctClass == null) {
				CtClass superClass = classPool.get("com.polymathiccoder.averroes.meta.model.AnnotatedDecorator");
				ctClass = classPool.makeClass(generatedClassName);
				ctClass.setSuperclass(superClass);
				ctClass.setModifiers(Modifier.PUBLIC);				
				
				CtConstructor ctConstructor = CtNewConstructor.make(generateConcreteDecoratorClassContructorForAnnotationType(annotationType), ctClass);
				ctConstructor.setModifiers(Modifier.PUBLIC);
				ctClass.addConstructor(ctConstructor);
			
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
	
	private static String generateConcreteDecoratorClassContructorForAnnotationType(Class<? extends Annotation> annotationType) {
        Template template = VELOCITY_ENGINE.getTemplate("META-INF/concreteDecoratorClassContructor.vm");
        VelocityContext context = new VelocityContext();
        context.put("annotation", annotationType);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
	}
}
