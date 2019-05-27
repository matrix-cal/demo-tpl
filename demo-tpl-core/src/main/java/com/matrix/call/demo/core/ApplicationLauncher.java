package com.matrix.call.demo.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Scanner;

/**
 * ApplicationLauncher
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class ApplicationLauncher {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.matrix.call.demo.core.demo016ForkJoin.AppDemo.class);


    public static void main(String[] args) {

        // spring-boot start
        ConfigurableApplicationContext ctx = SpringApplication.run(ApplicationLauncher.class, args);
        Environment environment = ctx.getBean(Environment.class);
        System.out.println(environment.getProperty("server.port"));
        System.out.println("Hello world! ");


        // hang to show
        Scanner sc = new Scanner( System.in );
        System.out.print( "Please enter a string : " );
        String inputVal = sc.next();
        LOGGER.warn(inputVal);
        System.out.print( "Your input is :" + inputVal );

        // shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("ready to exit");
                SpringApplication.exit(ctx);
            }
        }));
        System.exit(0);
    }
}
