package com.matrix.call.meite.core.demo002Thread;

import com.matrix.call.common.core.utils.CommonUDFUtils;

/**
 * 有T1、T2、T3三个线程，你怎样保证T2在T1执行完后执行，T3在T2执行完后执行
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemoJoin {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for(int i=0; i< 5; i++) {
                System.out.println("t1:" + i);
                CommonUDFUtils.sleep(100L);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {}
            for(int i=0; i< 5; i++) {
                System.out.println("t2:" + i);
                CommonUDFUtils.sleep(100L);
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {}
            for(int i=0; i< 5; i++) {
                System.out.println("t3:" + i);
                CommonUDFUtils.sleep(100L);
            }
        });

        t1.start();
        t2.start();
        t3.start();
        Thread.yield();
        System.out.println("+++done");
        System.out.println(AppDemoSyncTest.demoName);
    }

}
