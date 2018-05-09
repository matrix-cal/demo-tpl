package com.douyu.ocean.demo.core.demo8Lambda;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {


    public static void test01() {
        List<String> list = Arrays.asList("a", "b", "d");
        // 极简模式
        System.out.println("#####极简模式");
        list.forEach(s -> System.out.println(s));

        // 带参数类型模式
        System.out.println("#####带参数类型模式");
        list.forEach((String e) -> System.out.println(e));

        // 多条处理语句
        System.out.println("#####多条处理语句");
        list.forEach(e -> {
            System.out.print(e);
            System.out.println(e);
        });

        // 隐式转换变量为final
        int firstWord = 1;
        System.out.println("#####test");
        // firstWord = 2;
        list.forEach(e -> {
            System.out.println(firstWord + e);
        });

        // 自动推断return 返回值
        System.out.println("#####自动推断return 返回值");
        list.sort((e1, e2) -> -e1.compareTo(e2));
        list.forEach(e -> System.out.println(e));
        System.out.println("#####");

    }

    public static void test02() {

    }


    public static void main(String[] args) throws IOException {
        test01();
    }
}
