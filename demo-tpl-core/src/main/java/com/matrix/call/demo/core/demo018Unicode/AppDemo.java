package com.matrix.call.demo.core.demo018Unicode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {


    private static final Logger LOGGER = LoggerFactory.getLogger(AppDemo.class);



    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
        String orginStr = "中国人";
        String errorStr = new String(orginStr.getBytes("GBK"), "UTF-8");
        System.out.println(errorStr);
        String errorStr2 = new String(errorStr.getBytes("UTF-8"), "GBK");
        System.out.println(errorStr2);

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
