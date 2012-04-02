package com.polymathiccoder.averroes.meta.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtNewConstructor;
import javassist.LoaderClassPath;
import javassist.Modifier;
import javassist.NotFoundException;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class MetaModelCodeGenerator {
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
		//Access and name
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("public AnnotatedWith")
			.append(annotationType.getSimpleName())
		//Parameters
			.append("(Annotated annotated");
		if (annotationType.getDeclaredMethods().length != 0) {
			stringBuilder.append(", ")
				.append(Joiner.on(", ").join(Lists.transform(Arrays.asList(annotationType.getDeclaredMethods()), new MethodParameterTypeAndNameExtractor())));
		}
		stringBuilder.append(") {\n")
		//Body
			.append("\tsuper(annotated);\n");
		for (Method attribute : annotationType.getDeclaredMethods()) {
			stringBuilder.append("\t((com.google.common.collect.HashMultimap) decoratedAnnotated.getAnnotationTypesAndData()).put(")
				.append(annotationType.getSimpleName())
				.append(".class, org.apache.commons.lang3.tuple.ImmutablePair.of(\"")
				.append(attribute.getName())
				.append("\", ")
				.append(attribute.getName())
				.append("));\n");
		}
		
		if (annotationType.getDeclaredMethods().length == 0) {
			stringBuilder.append("\t((com.google.common.collect.HashMultimap) decoratedAnnotated.getAnnotationTypesAndData()).put(")
				.append(annotationType.getSimpleName())
				.append(".class, null);\n");
		}
		
		stringBuilder.append("}");
				
		return stringBuilder.toString();
	}
	
	private static class MethodParameterTypeAndNameExtractor implements Function<Method, String> {
	    public String apply(Method method) {
	    	return method.getReturnType().getCanonicalName() + " " + method.getName();
	    }    
	}
}
