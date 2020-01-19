package com.matrix.call.demo.core.demo000Test;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo7 {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppDemo7.class);

    public String xuanwuName;

    public String getXuanwuName() {
        return xuanwuName;
    }

    public void setXuanwuName(String xuanwuName) {
        this.xuanwuName = xuanwuName;
    }

    public static void main(String[] args) {
        String test = "weiqi test ${entity.xuanwuName}, other test is ${otherName:-bbbbb}";
        Map<String, Object> map = Maps.newConcurrentMap();
        map.put("entity.xuanwuName", "aaaa");
        //map.put("entity.otherName", "bbb");
        String result = StrSubstitutor.replace(test, map);
        System.out.println(result);
        String format = String.format("%.2f", 12345f);//test
        System.out.println(format);
    }



}
