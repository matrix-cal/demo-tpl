package com.douyu.ocean.demo.core.demo010Lambda;

/**
 * ConverterFactory
 *
 * @author weiqi
 * @create 2018-05-15 9:29:00
 */
@FunctionalInterface
public interface ConverterFactory<F, T> {


    public T convert(F from);
//    public T convert2(F from);

    public default void testDefault() {
        System.out.println("default FunctionalInterface");
    }
    public static void testDefaultStatic() {
        System.out.println("static FunctionalInterface");
    }

}
