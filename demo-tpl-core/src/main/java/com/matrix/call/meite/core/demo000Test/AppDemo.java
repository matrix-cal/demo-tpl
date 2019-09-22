package com.matrix.call.meite.core.demo000Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {

    public static void main(String[] args) {
        System.out.println("{}");
        String jsonStr = "{\"age\": 23}";
        JSONObject root = JSON.parseObject(jsonStr);
        //System.out.println(root.getJSONObject("person").getJSONObject("educate").getString("school"));
        System.out.println(root.fluentRemove("person"));


    }
}
