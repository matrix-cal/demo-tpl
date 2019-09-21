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
public class AppDemo {


    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationLauncher.class);

    public static ExecutorService consumeExecutorPool = new ThreadPoolExecutor(
            110,
            110,
            30,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10));

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        long startTime = System.currentTimeMillis();

        // 并行任务1
        Runnable t1 = () ->{
            sleep(1000L);
            LOGGER.info("任务1完成");
            countDownLatch.countDown();
        };

        // 任务2
        Runnable t2 = () ->{
            try {
                // 等待任务1
                countDownLatch.await();
                //countDownLatch.await(100L, TimeUnit.MILLISECONDS);
                long endTime = System.currentTimeMillis();
                LOGGER.info("任务2完成, 任务1总共耗时: {} ms",(endTime - startTime));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };


        //执行任务2
        consumeExecutorPool.submit(t2);
        consumeExecutorPool.submit(t2);

        //执行任务1
        for (int i = 0; i < 100; i++) {
            consumeExecutorPool.submit(t1);
        }


        //关闭线程池
        consumeExecutorPool.shutdown();
        if (!consumeExecutorPool.awaitTermination(3, TimeUnit.SECONDS)) {
            consumeExecutorPool.shutdownNow();
            System.out.println("线程池关闭1");
        } else {
            System.out.println("线程池关闭2");
        }

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
