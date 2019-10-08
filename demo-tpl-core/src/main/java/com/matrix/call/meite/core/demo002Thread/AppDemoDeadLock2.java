package com.matrix.call.meite.core.demo002Thread;

import com.matrix.call.common.core.utils.CommonUDFUtils;

import java.util.Random;

/**
 * 死锁
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemoDeadLock2 implements Runnable {

    public int ticket = -1;
    public int num = 0;
    public static byte[] lock01 = new byte[0];
    public static byte[] lock02 = new byte[0];
    public static byte[] lock03 = new byte[0];

    public AppDemoDeadLock2(int ticket) {
        this.ticket = ticket;
        //this.num = num++;
    }

    @Override
    public void run() {

        int workTime = new Random().nextInt(1000) + 800;
        int workTime2 = 3000;
        //CommonUDFUtils.sleep(workTime);
        System.out.println(Thread.currentThread().getName()+" before sync, sleep :"+workTime);
        synchronized (lock01) {
            CommonUDFUtils.sleep(workTime2);
            System.out.println(Thread.currentThread().getName()+" in     sync lock01, sleep :"+workTime);
            synchronized (lock02) {
                CommonUDFUtils.sleep(workTime);
                System.out.println(Thread.currentThread().getName()+" in     sync lock02, sleep :"+workTime);
            }

        }
        System.out.println(Thread.currentThread().getName()+" after     sync, sleep :"+workTime);
    }

    public void sale() {



    }


    public static void main(String[] args) throws InterruptedException {
        System.out.println("++++++++ main start ++++++++++++");

        AppDemoDeadLock2 sharedObj = new AppDemoDeadLock2(100);
        Thread t1 = new Thread(sharedObj);
        t1.setName("t1");
        t1.start();
        //dead lock
        int workTime2 = 3000;
        int workTime = new Random().nextInt(1000) + 800;
        synchronized (lock02) {
            CommonUDFUtils.sleep(workTime2);
            System.out.println(Thread.currentThread().getName()+" in     sync lock02, sleep :"+workTime2);
            synchronized (lock01) {
                CommonUDFUtils.sleep(workTime);
                System.out.println(Thread.currentThread().getName()+" in     sync lock01, sleep :"+workTime);
            }
        }
        System.out.println(Thread.currentThread().getName()+" after     sync, sleep :"+workTime);
        //t1.join();
        System.out.println("++++++++ main end ++++++++++++");

    }

}

