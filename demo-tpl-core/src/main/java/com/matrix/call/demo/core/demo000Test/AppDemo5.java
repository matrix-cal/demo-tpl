package com.matrix.call.demo.core.demo000Test;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo5 {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppDemo5.class);
    private String name = "hello, world";

    public static void main(String[] args) throws InterruptedException {
        Map valuesMap = new HashMap();
        AppDemo5 demo = new AppDemo5();
        valuesMap.put("demo", demo);

        String templateString = "hello, ${demo.name}";
        StrSubstitutor sub = new StrSubstitutor(valuesMap);

        String resolvedString = sub.replace(templateString);
        System.out.println(resolvedString);

        double i = 100.12d;
        System.out.println((int) i);
        Long d = 11L;
        System.out.println(String.valueOf(d));
    }

    public static int cal() {

        int j = 1 / 0;
        return j;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
