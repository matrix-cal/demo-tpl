package com.matrix.call.demo.core.demo006SystemEnv;

import java.util.Properties;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {

    public static void main(String[] args) {
        // 属性类
        Properties properties = System.getProperties();

        // IO临时文件夹
        System.out.println(properties.getProperty("java.io.tmpdir"));

        // JRE的安装目录
        System.out.println(properties.getProperty("java.home"));

        // 当前用户目录
        System.out.println(properties.getProperty("user.home"));

        // 启动Java进程时所在的目录
        System.out.println(properties.getProperty("user.dir"));
        System.out.println(properties.getProperty("os.name"));

        // 上述不是环境变量, 只是java运行的一些属性, 真正获取系统环境变量的方法
        String path = System.getenv("PATH");
        // String path = System.getenv("PATH"); MAC中 path和 PATH不同!!!
        String[] pathItems = path.split((String) properties.get("path.separator"));
        for (String pathItem : pathItems) {
            System.out.println(pathItem);
        }


        int one_million = 10;
        System.out.println(one_million);


    }
}
