package com.douyu.ocean.demo.core.demo003Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // 类或接口
public @interface ClassInfo {

    String value() default "helloAnnotationClass";
}
