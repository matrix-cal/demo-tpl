package com.douyu.ocean.demo.core.demo010Lambda;

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
        List<String> list = Arrays.asList("d", "b", "a", "c");
        /*
        // 匿名函数, 类是确定的, 方法又只有一个, 所以可以用lambda表达式
        // 官方说法: 任意只包含一个抽象方法的接口，我们都可以用来做成lambda表达式, 加上@FunctionalInterface 标注
        // 由于默认方法不是抽象的，因此你可以在你的函数式接口里任意添加默认方法
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

        /*
        // 自定义函数式接口
        System.out.println("#####FunctionalInterface-01");
        ConverterFactory cf = new ConverterFactory() {
            @Override
            public Object convert(Object from) {
                return null;
            }
        };
        ConverterFactory<String, Integer> cf2 = (from) -> Integer.parseInt(from);
        Integer convertNum = cf2.convert("100");
        System.out.println(++convertNum);
        */

        /*
        // 静态方法引用
        System.out.println("#####Colon-01");
        ConverterFactory<String, Integer> cf2 =Integer::parseInt;
        Integer convertNum = cf2.convert("100");
        System.out.println(++convertNum);

        */

        /*
        // 有一个参数，并且无返回值
        System.out.println("##### One param no return ");
        Consumer<Integer> consumer = (x) -> System.out.println(x);
        consumer.accept(5);
        */

        /*
        // 有一个参数，并且有返回值
        System.out.println("##### One param one return ");
        Function<String, Integer> fun = (from) -> {
            int result = Integer.parseInt(from);
            return result;
        };
        System.out.println(fun.apply("100") + 1);
        */
        /*
        // 两参数，并且有返回值
        System.out.println("##### Two param one return ");
        BiFunction<Integer, Integer, String> fun = (param1, param2) -> {
            String result = param1 + param2 +"";
            return result;
        };
        System.out.println(fun.apply(1, 2) + 1);
        */

    }



    public static void main(String[] args) throws IOException {
        test01();
    }
}
