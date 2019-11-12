package com.matrix.call.meite.core.demo002Thread;

import com.google.common.collect.Lists;
import com.matrix.call.common.core.utils.CommonUDFUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.List;

/**
 * ThreadLocal
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemoThreadNotify {
    volatile byte[] lock = new byte[0];
    public volatile static int Threshold = 20;



    public static void main(String[] args) {
        List<Goods> initList = Lists.newArrayList();

        Producer producer = new Producer(initList);
        Consumer consumer = new Consumer(initList);

        for(int i=0; i<1; i++) {
            Thread p = new Thread(producer);
            p.start();
        }
        for(int j=0; j<5; j++) {
            Thread c = new Thread(consumer);
            c.start();
        }


    }

}

class Producer implements Runnable{
    public volatile List<Goods> stageList = Lists.newArrayList();


    public Producer(List<Goods> stageList) {
        if (stageList == null) {
            stageList = Lists.newArrayList();
        }
        this.stageList = stageList;
    }

    public void produce() {
        synchronized (stageList) {
            while (stageList.size() >= AppDemoThreadNotify.Threshold) {
                try {
                    System.out.println("list is full");
                    stageList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String goodsName = Thread.currentThread().getName() +"-"+ DateFormatUtils.format(new Date(), "HHmmssSSS");
            Goods goods = new Goods(0, goodsName);
            stageList.add(goods);
            stageList.notifyAll();
        }

    }

    @Override
    public void run() {
        while (true) {
            produce();
            CommonUDFUtils.sleep(10L);
        }
    }
}

class Consumer implements Runnable{
    public volatile List<Goods> stageList = Lists.newArrayList();

    public Consumer(List<Goods> stageList) {
        if (stageList == null) {
            stageList = Lists.newArrayList();
        }
        this.stageList = stageList;
    }

    public  int consumer(){
        synchronized (stageList) {
            while (stageList.size() ==0) {
                try {
                    stageList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int retVal = stageList.size();
            for (int i = 0; i < stageList.size(); i++) {
                System.out.println(stageList.get(i).getName());
                stageList.remove(i);
            }
            stageList.notifyAll();
            return retVal;
        }

    }

    @Override
    public void run() {
        while (true) {
            int consumerNum = consumer();
            System.out.println("======="+consumerNum+ "=====" + Thread.currentThread().getName());
            CommonUDFUtils.sleep(1000L);
        }
    }
}


class Goods {
    private int id;
    private String name;

    public Goods(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}