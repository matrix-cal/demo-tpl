package com.douyu.ocean.demo.core.demo003Annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        Class<?> cls = TestRuntimeAnnotation.class;
        // test class 注解
//        Constructor<?>[] constructors = cls.getConstructors();
        sb.append("class 注解:").append("\n");
        ClassInfo classInfo = cls.getAnnotation(ClassInfo.class);
        if (classInfo != null) {
            sb.append(Modifier.toString(cls.getModifiers())).append(" ")
                    .append(cls.getSimpleName()).append("\n");
            sb.append("注解值: ").append(classInfo.value()).append("\n\n");
        }
        // test field 注解
        sb.append("Field 注解: ").append("\n");
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            FieldInfo fieldInfo = field.getAnnotation(FieldInfo.class);
            if (fieldInfo != null) {
                sb.append(Modifier.toString(field.getModifiers())).append(" ")
                        .append(field.getType().getSimpleName()).append(" ")
                        .append(field.getName()).append("\n");
                sb.append("注解值: ").append(Arrays.toString(fieldInfo.value())).append("\n\n");
            }
        }

        System.out.println(sb.toString());

    }

    public void test() {
        System.out.println("hello package");
    }
}
