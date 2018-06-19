package com.douyu.ocean.demo.core.demo001Enum;

public enum DouyuEnum {
    MOTHER(1, "wuhongyu"),
    FATHER(2, "weiqi"),
    SON(3, "matrixcal");


    private int num;
    private String name;


    DouyuEnum(int num, String name) {
        this.num = num;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name+":"+this.num;
    }

    public static void main(String[] args) {
        DouyuEnum test = DouyuEnum.FATHER;
        System.out.println(test.name);
        System.out.println(test.num);
        DouyuEnum[] values = DouyuEnum.values();
        for (DouyuEnum value : values) {
            System.out.println(value.toString());
        }
    }
}
