package com.polymathiccoder.averroes.meta.validation.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ForTypeIncluding {
	int exactly() default 1;
	int atLeast() default 0;
	int atMost() default Integer.MAX_VALUE;
	Class<? extends Annotation> value();
}
