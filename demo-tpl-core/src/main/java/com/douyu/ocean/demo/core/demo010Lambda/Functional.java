package com.douyu.ocean.demo.core.demo010Lambda;

public interface Functional {
    void method();

    /**
     * test
     */
    default void defaultMethod01() {
        System.out.println("i am defaultMethod01");
    }
    default void defaultMethod02() {
        System.out.println("i am defaultMethod02");
    }

    static void staticMethod01() {
        System.out.println("i am staticMethod01");
    }

    static void staticMethod02() {
        System.out.println("i am staticMethod02");
    }
}


class DemoImpl01 implements Functional {

    @Override
    public void defaultMethod01() {
        System.out.println("i am override defaultMethod01(demo01)");
    }

    @Override
    public void method() {
        System.out.println("i am override method(demo01)");
    }
}


class DemoImpl02 implements Functional {


    @Override
    public void method() {
        System.out.println("i am override method(demo01)");
    }
}