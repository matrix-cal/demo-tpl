package com.matrix.call.demo.core.demo003Annotation;

/**
 * TestRuntimeAnnotation
 *
 * @author weiqi
 * @create 2018-05-07 19:51:00
 */
@ClassInfo("Test class annotation")
public class TestRuntimeAnnotation {

    @FieldInfo(value = {1, 2})
    public String fieldInfo = "";
    @FieldInfo(value = {3, 4})
    public String secondName = "";

    public static String testMethod() {
        return TestRuntimeAnnotation.class.getSimpleName();
    }
}
