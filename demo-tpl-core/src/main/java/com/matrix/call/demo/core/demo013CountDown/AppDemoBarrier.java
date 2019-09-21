package com.matrix.call.demo.core.demo013CountDown;

import com.matrix.call.demo.core.ApplicationLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemoBarrier {


    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationLauncher.class);

    public static ExecutorService consumeExecutorPool = new ThreadPoolExecutor(
            110,
            110,
            30,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10));

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
