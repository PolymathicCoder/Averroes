package com.polymathiccoder.averroes.meta.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Discoverable {
}
