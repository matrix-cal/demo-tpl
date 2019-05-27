package com.matrix.call.demo.core.demo001Enum;

/**
 * MyEnum
 *
 * @author weiqi
 * @create 2018-04-21 10:47:00
 */
public enum MyEnum {
    RED(3, "红"),
    GREEN(2, "绿"),
    YELLOW(1, "黄");

    private int value;
    private String color;

    MyEnum(int value, String color) {
        this.value = value;
        this.color = color;
    }

    public int getValue(){
        return value;
    }
    public String getColor(){
        return color;
    }

    public static MyEnum getEnumByValue(int value) {
        for (MyEnum myEnum : MyEnum.values()) {
            if (myEnum.value == value) {
                return myEnum;
            }
        }
        throw new IllegalArgumentException("No element matches " + value);
    }
}
