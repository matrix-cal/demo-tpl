package com.matrix.call.demo.core.demo003Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * FieldInfo
 *
 * @author weiqi
 * @create 2018-05-07 19:30:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) // 字段
public @interface FieldInfo {

    int[] value();

}
