package com.matrix.call.meite.core.demo002Thread;

import com.matrix.call.common.core.utils.CommonUDFUtils;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * 一个厕所只有3个坑位，但是有10个人来上厕所
 * 带流量控制的pool
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemoSemaphore implements Runnable{

    public Semaphore wcRoomNum;

    public AppDemoSemaphore(Semaphore wcRoomNum) {
        this.wcRoomNum = wcRoomNum;
    }


    @Override
    public void run() {
        try {
            wcRoomNum.acquire();
            System.out.println(Thread.currentThread().getName() +"-获取坑位");
            int workTime = new Random().nextInt(1000) + 800;
            CommonUDFUtils.sleep(workTime);
            //System.out.println(Thread.currentThread().getName() +"-释放坑位");
            wcRoomNum.release();
            System.out.println("-----当前可用坑位"+ wcRoomNum.availablePermits());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Semaphore wcRoomNum = new Semaphore(5);
        AppDemoSemaphore wc= new AppDemoSemaphore(wcRoomNum);
        for (int i = 1; i <31; i++) {
            new Thread(wc, Integer.toString(i)).start();
        }
    }
}
