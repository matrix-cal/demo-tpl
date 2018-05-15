package com.douyu.ocean.demo.core.demo010Lambda;

/**
 * ConverterFactory
 *
 * @author weiqi
 * @create 2018-05-15 9:29:00
 */
public interface ConverterFactory<F, T> {


    public T convert(F from);

}
