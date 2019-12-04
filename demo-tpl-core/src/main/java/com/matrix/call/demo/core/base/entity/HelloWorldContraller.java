package com.matrix.call.demo.core.base.entity;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@RestController
public class HelloWorldContraller {

    private static long index = 0L;
    private List<byte[]> list = new ArrayList<>();

    @RequestMapping("/")
    public String sayHello() {
        return "Hello,World! " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
    }

    @RequestMapping("/loop/{loopNum}")
    public String sayHelloLoop(@PathVariable("loopNum") long loopNum) {
        /*
        //java.lang.StackOverflowError
        try {
            stackError();
        } catch (Throwable e) {
            System.out.println("$$$$index:" +index);
            e.printStackTrace();
        }
        */

        //java.lang.OutOfMemoryError: Java heap space
        for (long i = 0; i < loopNum; i++) {
            try {
                list.add(new byte[1024 * 1024]);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }



        //return
        return "Hello,Loop  (" + loopNum + ") "
                + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
    }

    private void stackError(){
        index++;
        stackError();
    }

}
