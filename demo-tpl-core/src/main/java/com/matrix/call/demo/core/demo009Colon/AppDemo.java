package com.matrix.call.demo.core.demo9Colon;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {

    /**
     * 方法引用, 两个冒号; 个人理解是 只有一句话的lambda表达式,且input param与 形参相同; 常用语foreach
     */
    public static void test01() {
        List<String> list = Arrays.asList("a","c", "b", "d");
        List<Integer> list2 = Arrays.asList(1, -1, -2, 2);

        //# 对象引用::实例方法名, 实例上的实例方法引用, instance::method
        PrintStream out = System.out;
        list.forEach(out::println);


        //# 类名::实例方法名, Class::method, 类的实例方法引用, 首先判断是不是静态方法, 不是静态方法, 则把第一个参数作为实例对象,
        BiPredicate<String, String> b = String::equals;
        System.out.println(b.test("abc", "abcd"));

        //# 类名::静态方法名,静态方法引用, Class::static_method
        Consumer<Integer> function = Math::abs;
        list2.forEach(function);

        //# 构造器, 构造方法引用, Class< T >::new
        //Function<String, StringBuffer> function1 = StringBuffer::new;
        Function<Integer, Integer> function1 = Integer::new;
        Integer num = function1.apply(123);
        System.out.println(num);
    }




    public static void main(String[] args) throws IOException {
        test01();
//        test02();
    }
}
