package com.matrix.call.demo.core.demo010Lambda;

import java.io.IOException;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo3 {

    static double sum = 0;

    public  void foo() {
        for (int i = 0; i < 0x77777777; i++) {
            sum += Math.sqrt(i);
        }
    }

    public static void bar() {
        for (int i = 0; i < 50_000_000; i++) {
            new Object().hashCode();
        }
    }





    public static void main(String[] args) throws IOException, InterruptedException {
        /*System.out.println("start");
        System.out.println(0x77777777);
        Thread t = new Thread((new AppDemo3())::foo);
        t.start();
        t.join();
        System.out.println(sum);*/
        System.out.println(Integer.MAX_VALUE);
    }
}
