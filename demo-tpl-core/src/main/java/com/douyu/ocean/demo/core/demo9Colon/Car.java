package com.douyu.ocean.demo.core.demo9Colon;

import java.util.function.Supplier;

/**
 * Car
 *
 * @author weiqi
 * @create 2018-05-10 21:29:00
 */
public class Car {

    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(Car car) {
        System.out.println("Collided " + car.toString());
    }

    public static void follow(Car another) {
        System.out.println("Following the " + another.toString());
    }
    public void repair() {
        System.out.println("repair " + this.toString());
    }

}
