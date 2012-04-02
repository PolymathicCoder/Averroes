package com.polymathiccoder.averroes.meta.processing;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import javassist.Modifier;
import javassist.NotFoundException;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.polymathiccoder.averroes.meta.model.AnnotatedDecorator;

public class MetaProcessingCodeGenerator {
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
		//Access, name, and params
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("public Annotated execute(AnnotatedElement annotatedElement) {\n")
		//Body
		.append("\t")
		.append(annotationType.getSimpleName())
		.append(" annotation = null;\n")
		.append("\tif (annotatedElement.getAnnotatedElement() instanceof Class) {\n")
		.append("\t\tannotation = ((Class) annotatedElement.getAnnotatedElement()).getAnnotation(")
		.append(annotationType.getSimpleName())
		.append(".class);\n")
		.append("\t} else if (annotatedElement.getAnnotatedElement() instanceof Field) {\n")
		.append("\t\tannotation = ((Field) annotatedElement.getAnnotatedElement()).getAnnotation(")
		.append(annotationType.getSimpleName())
		.append(".class);\n\t}\n")
		.append("\tif (annotation == null) return annotatedElement;\n")
		.append("\treturn new AnnotatedWith")
		.append(annotationType.getSimpleName())
		.append("(annotatedElement");
		if (annotationType.getDeclaredMethods().length != 0) {
			stringBuilder.append(", ")
				.append(Joiner.on(", ").join(Lists.transform(Arrays.asList(annotationType.getDeclaredMethods()), new MethodCallExtractor())));
		}
		stringBuilder.append(");\n");
		stringBuilder.append("}");
		
		return stringBuilder.toString();
	}
	
	private static class MethodCallExtractor implements Function<Method, String> {
	    public String apply(Method method) {
	    	return "annotation." + method.getName() + "()";
	    }    
	}
}
