package com.matrix.call.demo.core.demo000Test;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {

    public static void main(String[] args) {
        System.out.println("cpuNum:"+ Runtime.getRuntime().availableProcessors());
        System.out.println("freeMemory:"+ Runtime.getRuntime().freeMemory()/1024/1024);
        System.out.println("totalMemory:"+ Runtime.getRuntime().totalMemory()/1024/1024);
        System.out.println("maxMemory:"+ Runtime.getRuntime().maxMemory()/1024/1024);
        AppDemo appDemo = new AppDemo();
        appDemo.test();
    }
    public void test(){
        System.out.println(this.getClass().getSimpleName());
    }
}
