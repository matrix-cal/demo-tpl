package com.douyu.ocean.demo.core.demo7TryFinally;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {

    public static void test() {
        try {
            System.out.println("i am in try01");
            System.out.println(1 / 0);
            System.out.println("i am in try02");
        } catch (Exception e) {
            System.out.println("i am in catch");
        } finally {
            System.out.println("i am in finally");
        }
        System.out.println("i am in out");
    }

    /**
     * try-with-resources与try一样，可以与catch和finally一起使用，catch/finally会在try-with-resources自动关闭资源之后执行。
     * @throws IOException
     */
    public static void test2() {
        System.out.println("i am in in");
        try (BufferedReader br = new BufferedReader(new FileReader("D:/session.xts"))) {
            System.out.println(br.readLine());
            System.out.println("i am in try01");
            System.out.println(1/0);
            System.out.println("i am in try02");
        } catch (Exception e) {
            System.out.println("i am in catch");
            e.printStackTrace();
        } finally {
            System.out.println("i am in finally");
        }
        System.out.println("i am in out");
    }


    public static void main(String[] args) throws IOException {
        test2();
    }
}
