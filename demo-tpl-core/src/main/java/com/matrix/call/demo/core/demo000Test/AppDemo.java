package com.matrix.call.demo.core.demo000Test;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {

    public static void main(String[] args) {
        System.out.println("hello demo-tpl");
        StringBuffer sb = new StringBuffer("aaa");

        Long param1 = 1111L;
        AtomicLong param2 = new AtomicLong(33);
        changeObjVal(param1,param2,  sb);
        System.out.println(param1);
        System.out.println(param2);
        System.out.println(sb.toString());

    }

    private static void changeObjVal(Long param1, AtomicLong param2 ,StringBuffer sb) {
        param1 = param1 + 888L;
        param2.addAndGet(11);
        sb.append("-changed");

    }

    private static int changeObjVal(long param1, AtomicLong param2 ) {
        param1 = param1 + 888L;
        param2.addAndGet(11);
        return 1;
    }


}
