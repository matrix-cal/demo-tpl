package com.douyu.ocean.demo.core.demo014Tuple;

import com.google.common.collect.Maps;
import org.apache.commons.collections.keyvalue.DefaultMapEntry;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

/**
 * 其实tuple在参数少的时候, 还算可以用, 参数多了的话, 返回后 , 别人使用就有问题了, 还不如封装个数组
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {


    public static void test01() {
        //# Guava
        Map.Entry<String, Integer> tuple_guava = Maps.immutableEntry("test1", 1);
        String formatStr = String.format("guava: key=%s, val=%s", tuple_guava.getKey(), tuple_guava.getValue());
        System.out.println(formatStr);


        //# Apache commons-lang3, 这个好用, 用这个
        ImmutableTriple<String, Integer, String> tuple_lang3 = new ImmutableTriple<>("test2", 2, "2X");
        formatStr = String.format("lang3_1: key=%s, middle=%s, val=%s", tuple_lang3.getLeft(), tuple_lang3.getMiddle(), tuple_lang3.getRight());
        System.out.println(formatStr);

        ImmutablePair<String, Long> tuple_lang3_20 = ImmutablePair.of("test20", 20L);
        formatStr = String.format("lang3_2: key=%s, val=%s", tuple_lang3_20.getLeft(), tuple_lang3_20.getRight());
        System.out.println(formatStr);


        //# Apache commons-collections
        Map.Entry<String,Integer> tuple_apache_collections = new DefaultMapEntry("test3", 3);
        formatStr = String.format("Apache commons-collections: key=%s, val=%s", tuple_apache_collections.getKey(), tuple_apache_collections.getValue());
        System.out.println(formatStr);

        //jdk6
        Map.Entry<String,Integer> test4 = new AbstractMap.SimpleEntry("test4", 4);


    }



    public static void main(String[] args) throws IOException {
        test01();
    }
}
