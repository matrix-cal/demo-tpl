package com.douyu.ocean.demo.core.demo001Enum;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class Demo001 {

    public static void main(String[] args) {
        System.out.println("hello demo-tpl");
        NormalEntity normalEntity = new NormalEntity();
        normalEntity.setId(1);
        normalEntity.setLight(MyEnum.GREEN);
        System.out.println(normalEntity.getLight().getValue());
        for (MyEnum myEnum : MyEnum.values()) {
            System.out.println(myEnum.name()+":"+myEnum.getValue());
        }
        System.out.println(MyEnum.valueOf("GREEN"));
        System.out.println(MyEnum.getEnumByValue(1));

    }
}
