package com.douyu.ocean.demo.core.demo010Lambda;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {


    public static void test01() {
        List<String> list = Arrays.asList("d", "b", "a", "c");

        //# 2个 参数, 有返回值 (加Bi的都是2个参数的)
        BiFunction<String, String, Integer> bit = (x, y) ->{
            System.out.println(x + y);
            return 0;
        };
        bit.apply("1  params", " & 1  return");

        //# 无参数, 无返回值
        Runnable runable = ()-> {
            System.out.println("no params & no return");
        };
        runable.run();
        //# ############################################################

        //# 无 参数, 有返回值
        Predicate<Integer> predicate = x -> {
            if (x % 2 == 0) {
                System.out.println("no  params & 1  return");
                return true;
            }
            return false;
        };
        predicate.test(1);
        //predicate.and();

        //# 有一个参数, 无返回值test
        Consumer<String> consumer = (x) -> {
            System.out.println(x);
        };
        consumer.accept("1  params & no return");
        // consumer.andThen()

        //# 有一个参数，并且有返回值
        Function<String, Integer> function = s -> {
            System.out.println(s);
            return 0;
        };
        Integer retVal = function.apply("1  params & 1  return");
        //function.compose();
        //function.andThen()

        //# 有一个参数，并且有返回值
        Supplier<String> supplier = () -> {
            return "no params & 1  return";
        };
        String soutInfo = supplier.get();
        System.out.println(soutInfo);
        // no other function




    }



    public static void main(String[] args) throws IOException {
        test01();
    }
}
