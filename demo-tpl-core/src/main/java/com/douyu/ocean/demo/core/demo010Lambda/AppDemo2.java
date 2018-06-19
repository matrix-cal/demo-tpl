package com.douyu.ocean.demo.core.demo010Lambda;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo2 {


    public static void test01() {
        List<String> list = Arrays.asList("d", "b", "a", "e", "c");

        //# input void, output void
        Runnable runnable = () ->{
            System.out.println("# input void, output void");
        };
        runnable.run();

        //# input void, output T
        Supplier<String> supplier = () -> {
            return "# input void, output T";
        };
        System.out.println(supplier.get());


        //# input T, output void
        Consumer<String> consumer = (obj) -> {
            System.out.println(obj);
        };
        consumer.accept("# input T, output void");


        //# input T, output R
        Function<String, String> function = (obj) -> {
            return "# " + obj;
        };
        String retVal = function.apply("input T, output R");
        System.out.println(retVal);

        //# input T, output boolean
        Predicate<Integer> predicate = (obj) -> {
            if (obj % 2 == 0) {
                return true;
            }
            return false;
        };
        if (predicate.test(2)) {
            System.out.println("# input T, output boolean");
        }






    }



    public static void main(String[] args) throws IOException {
        test01();
    }
}
