package com.matrix.call.demo.core.demo000Test;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {

    private AppDemo(){}

    public static class LazyHolder {

        public static final AppDemo APPDEMO = new AppDemo();
    }

    public AppDemo getInstance() {
        return LazyHolder.APPDEMO;
    }

    public static void main(String[] args) {


    }




}
