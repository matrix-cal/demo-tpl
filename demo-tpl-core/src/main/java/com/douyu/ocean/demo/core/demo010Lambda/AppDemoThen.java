package com.douyu.ocean.demo.core.demo010Lambda;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemoThen {


    public static void test01() {
        List<String> list = Arrays.asList("d", "b", "a", "c");


        //# ############################################################


        //# 有一个参数，并且有返回值
        Function<Integer, Integer> fun1 = e -> e + 3;
        Function<Integer, Integer> fun2 = e -> e * 3;
        Integer fun1_result = fun1.apply(10);
        Integer fun2_result = fun2.apply(10);
        System.out.println("fun1_result: "+ fun1_result);
        System.out.println("fun2_result: "+ fun2_result);

        Integer funAndThenResult_1 = fun1.andThen(fun2).apply(10);
        System.out.println("funAndThenResult_1: "+ funAndThenResult_1);

        Integer funAndThenResult_2 = fun2.andThen(fun1).apply(10);
        System.out.println("funAndThenResult_2: "+ funAndThenResult_2);

        Integer funComposeResult_3 = fun1.compose(fun2).apply(10);
        System.out.println("funComposeResult_3: "+ funComposeResult_3);
        Integer funComposeResult_4 = fun2.compose(fun1).apply(10);
        System.out.println("funComposeResult_4: "+ funComposeResult_4);


        //#########
        Function<Integer, String> fun11 = e -> e.toString();
        Function<String, Integer> fun22 = e -> Integer.parseInt(e);
        String apply22 = fun11.andThen(fun22).andThen(fun11).apply(8);
        System.out.println(apply22);


        Function<Integer, String> fun33 = e -> e.toString();
        Function<String, Integer> fun44 = e -> Integer.parseInt(e);
        Integer apply44 = fun44.compose(fun33).compose(fun44).compose(fun33).apply(8);
        System.out.println(apply44);

    }



    public static void main(String[] args) throws IOException {
        test01();
    }
}
