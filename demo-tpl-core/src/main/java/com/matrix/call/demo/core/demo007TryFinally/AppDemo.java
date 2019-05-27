package com.matrix.call.demo.core.demo007TryFinally;

import com.matrix.call.demo.core.demo007TryFinally.BufferedTestReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
        System.out.println("i am in in2");
        try {
            BufferedTestReader br = null;


            try {
                br = new BufferedTestReader("/EFI/r4411.txt");
                System.out.println(br.read());
                System.out.println("i am in try01");
                System.out.println(1/0);
                System.out.println("i am in try02");
            } catch (Exception e) {
                throw e;
            } finally {
                if(br != null) br.close();
            }

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
    public static void test2() throws IOException {
        System.out.println("i am in in2");
        try (BufferedTestReader br = new BufferedTestReader("/EFI/r4411.txt")) {
            System.out.println(br.read());
            System.out.println("i am in try01");
            System.out.println(1/0);
            System.out.println("i am in try02");

        } catch (Exception e) {
            System.out.println("i am in catch");
        } finally {

            System.out.println("i am in finally");
        }
        System.out.println("i am in out");
    }


    public static void main(String[] args) throws IOException {
        test2();
        System.out.println("+++++++等价于++++++++++++");
        test();
    }
}
