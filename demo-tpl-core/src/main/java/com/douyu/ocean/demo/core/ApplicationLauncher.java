package com.douyu.ocean.demo.core;

import com.douyu.ocean.demo.core.demo003Annotation.AppDemo003;

/**
 * ApplicationLauncher
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class ApplicationLauncher {

    public static void main(String[] args) {
        System.out.println("hello demo-tpl");
        AppDemo003 demo003 = new AppDemo003();
        demo003.test();
    }
}
