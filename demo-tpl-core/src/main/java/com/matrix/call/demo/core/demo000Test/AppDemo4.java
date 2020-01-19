package com.matrix.call.demo.core.demo000Test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo4 {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppDemo4.class);


    public static void main(String[] args) throws InterruptedException {
        int runThreadNum = 3;
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("prigift-thread-%d").build();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(runThreadNum, runThreadNum,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                namedThreadFactory);

        for (int i = 1; i < 5; i++) {
            int finalI = i;
            threadPool.submit(() -> {
                int count = 0;
                while (true) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(finalI);
                    count++;
                    if (finalI == 1) {
                        if (count == 10) {
                            break;
                        }
                    } else {
                        if (count == 20) {
                            break;
                        }
                    }
                }
                return;
            });

        }

        threadPool.shutdown();
        while (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("check");
            continue;
        }
    }

    public static int cal() {

        int j = 1 / 0;
        return j;
    }


}
