package com.matrix.call.demo.core.demo000;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.matrix.call.demo.core.demo016ForkJoin.AppDemo.class);



    public static void main(String[] args) throws InterruptedException {

    }

    /**
     * 休眠辅助方法
     * @param milliSecond
     */
    public static void sleep(long milliSecond) {
        try {
            if (milliSecond <= 0L) {
                return;
            }
            Thread.sleep(milliSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
