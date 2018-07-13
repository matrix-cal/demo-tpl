package com.douyu.ocean.demo.core.demo011Stream;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {

    /**
     * Stream api  提供了一种高效且易于使用的处理数据方式
     * 创建Stream: 数组Arrays.stream() 或集合Collection.stream() 或由值 创建 Stream.xx
     * 中间操作: 筛选, 切割, 映射, 排序
     * 终止操作:
     */
    public static void test01() {
        //# 创建 Stream
        List<Integer> list1 = Arrays.asList(2, 1, 4, 3, 6, 5, 8, 6, 7);
        int[] list2 = {2, 1, 4, 3, 5, 8, 6, 7};
        String[] list3 = {"hello", "world"};
        list1.stream().filter(e -> e > 5).forEach(System.out::print);
        System.out.println();
        Arrays.stream(list2).filter(e -> e>6).forEach(System.out::print);
        System.out.println();
        Stream.of(2, 9, 3).filter(e -> e > 7).forEach(System.out::print);
        System.out.println();
        //Stream.iterate(1, x -> x + 1).forEach(System.out::println);

        //# 中间操作
        //## 筛选 filter, distinct
        System.out.println("\n筛选-filter: ");
        list1.stream().filter(e -> e > 5).forEach(System.out::print);
        System.out.println("\n筛选-distinct: ");
        list1.stream().distinct().forEach(System.out::print);

        //## 切割 skip, limit
        System.out.println("\n切割-skip-limit: ");
        list1.stream().skip(1).limit(3).forEach(System.out::print);

        //## 映射 map, flatMap(返回流 后合并成更大的流)
        System.out.println("\n映射map: ");
        list1.stream().map(x -> x * x).forEach(doubleX -> System.out.print(doubleX+", "));
        System.out.println("\n映射flatMap: ");
        Arrays.stream(list3).flatMap(str -> Arrays.stream(str.split(""))).distinct().forEach(str -> System.out.print(str+", "));

        //## 排序 sort,
        System.out.println("\n排序sort: ");
        list1.stream().distinct().sorted((x, y) -> x.compareTo(y)).forEach(System.out::print);

        //# 终止操作

        //## 查找与匹配 allMatch, findFirst, min
        System.out.println("\n");
        boolean b = list1.stream().allMatch(e -> e > 1);
        System.out.println("匹配 allMatch:" + b);

        Optional<Integer> first = list1.stream().findFirst();
        System.out.println("匹配 findFirst:" + first.orElse(null));

        Integer minVal = list1.stream().min(Integer::compare).orElse(null);
        System.out.println("匹配 minVal:" + minVal);

        //## 归约 reduce, 得到某个值
        Integer reduceResult1 = list1.stream().reduce(10, Integer::sum);
        System.out.println("reduce1: "+reduceResult1);
        Optional<Integer> reduceResult2 = list1.stream().reduce((x, y) -> x + y);
        System.out.println("reduce2: "+reduceResult2.orElse(0));

        //## 收集 collect, 得到某个集合
        ConcurrentMap<Integer, Integer> collectMap = list1.stream().collect(Collectors.toConcurrentMap(x -> x, y -> y+1, (x,y) -> x));
        System.out.println("collect: "+collectMap);

        //## test stream惰性, 即就仅仅执行stream()方法并不会跑, 只有执行终止操作时, 才回把数组丢进去
        Stream<Integer> streamTest = list1.stream();
        list1.set(0, 12);
        Integer reduceResultTest = streamTest.reduce(10, Integer::sum);

        System.out.println("reduceTest: "+reduceResultTest);
    }


    public static void main(String[] args) throws IOException {
        test01();
    }
}
