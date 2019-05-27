package com.matrix.call.demo.core.demo002HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {

    public static String testMethod(int id, String name) {
        System.out.println("testMethod-01");
        return "01";
    }

    public static int testMethod(int id, String name, String name2) {
        System.out.println("testMethod-02");
        return 1;
    }


    public static void main(String[] args) throws IOException {
        HttpServer hs = HttpServer.create(new InetSocketAddress(8888), 0);
        hs.setExecutor(null);
        hs.createContext("/test", new MyHander());
        hs.start();
    }
}
