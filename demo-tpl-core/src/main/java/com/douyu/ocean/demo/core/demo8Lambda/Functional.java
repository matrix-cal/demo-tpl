package com.douyu.ocean.demo.core.demo8Lambda;

@FunctionalInterface
public interface Functional {
    void method();

    /**
     * test
     */
    default void defaultMethod() {
        System.out.println("i am lambda");
    }
}
