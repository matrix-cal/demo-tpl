package com.matrix.call.meite.core.demo002Thread;

import com.matrix.call.common.core.utils.CommonUDFUtils;

/**
 * 死锁
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemoDeadLock implements Runnable {

    public int ticket = -1;
    public int num = 0;
    public byte[] lock01 = new byte[0];
    public byte[] lock02 = new byte[0];

    public AppDemoDeadLock(int ticket) {
        this.ticket = ticket;
        //this.num = num++;
    }

    @Override
    public void run() {


        while (this.ticket > 0) {
            synchronized (lock01) {
                CommonUDFUtils.sleep(100L);
                synchronized (lock02) {
                    CommonUDFUtils.sleep(100L);
                    sale();
                }
            }
            synchronized (lock02) {
                CommonUDFUtils.sleep(100L);
                synchronized (lock01) {
                    CommonUDFUtils.sleep(100L);
                    sale();
                }
            }
        }

    }

    public void sale() {
        if (ticket > 0) {
            CommonUDFUtils.sleep(50L);
            System.out.println(Thread.currentThread().getName() + "这是第 " + (100 - ticket + 1) + "票");
            this.ticket--;
        }


    }


    public static void main(String[] args) {
        AppDemoDeadLock sharedObj = new AppDemoDeadLock(100);
        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(sharedObj);
            t.setName("t" + i);
            t.start();
            //CommonUDFUtils.sleep(60L);
        }


    }

}

