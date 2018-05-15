package com.douyu.ocean.demo.core.demo010Lambda;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {


    public static void test01() {
        List<String> list = Arrays.asList("d", "b", "a", "c");
        /*
        // 匿名函数, 类是确定的, 方法又只有一个, 所以
        System.out.println("#####匿名函数");
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        list.forEach(s -> System.out.println(s));
        */
        /*
        // Lambda-01
        System.out.println("#####Lambda-01");
        Collections.sort(list, (s1, s2) -> {
            return s1.compareTo(s2);
        });
        list.forEach(s -> System.out.println(s));
        */
        /*
        // Lambda-02
        System.out.println("#####Lambda-02");
        Collections.sort(list, (s1, s2) -> s1.compareTo(s2));
        list.forEach(s -> System.out.println(s));
        */

        // 函数式接口
        System.out.println("#####Lambda-02");
        Collections.sort(list, (s1, s2) -> s1.compareTo(s2));
        list.forEach(s -> System.out.println(s));

    }



    public static void main(String[] args) throws IOException {
        test01();
    }
}
