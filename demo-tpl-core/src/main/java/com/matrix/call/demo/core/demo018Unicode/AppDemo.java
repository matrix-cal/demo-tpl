package com.matrix.call.demo.core.demo018Unicode;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {


    private static final Logger LOGGER = LoggerFactory.getLogger(AppDemo.class);



    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
        String orginStr = "中国人";
        String errorStr = new String(orginStr.getBytes("GBK"), "UTF-8");
        System.out.println(errorStr);
        String errorStr2 = new String(errorStr.getBytes("UTF-8"), "GBK");
        System.out.println(errorStr2);

        User u = new User();
        Child child = new Child();
        Toy toy1 = new Toy("airplane1", "air");
        Toy toy2 = new Toy("airplane2", "air");
        child.setToyList(Lists.newArrayList(toy1, toy2));
        u.setChildList(Lists.newArrayList(child));

        //System.out.println(u.getChildList().get(0).getToyList().get(1).getToyName());
        String toyName = Optional.ofNullable(u)
                .map(ou -> ou.getChildList())
                .map(chList -> chList.get(0))
                .map(ochild -> ochild.getToyList())
                //.map(ochild -> (List<Toy>)null)
                .map(toyList -> toyList.size()< 3 ? null : toyList.get(2))
                .map(toy -> toy.getToyName())
                .orElse("defaultToy");
        System.out.println(toyName);

        Child u2 = null;
        System.out.println((Child)(null));

    }

    /**
     * 休眠辅助方法
     * @param milliSecond
     */
    public static void sleep(long milliSecond) {
        try {
            if (milliSecond <= 0L) {
                return;
            }
            Thread.sleep(milliSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
