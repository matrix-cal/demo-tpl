package com.douyu.ocean.demo.core.demo003Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MethodInfo
 *
 * @author weiqi
 * @create 2018-05-07 19:34:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodInfo {

    String name() default "long";
    String data();
}
