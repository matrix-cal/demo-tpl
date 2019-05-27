package com.matrix.call.demo.core.demo001Enum;

public enum MatrixEnum {
    MOTHER(1, "wuhongyu"),
    FATHER(2, "weiqi"),
    SON(3, "matrixcal");


    private int num;
    private String name;


    MatrixEnum(int num, String name) {
        this.num = num;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name+":"+this.num;
    }

    public static void main(String[] args) {
        MatrixEnum test = MatrixEnum.FATHER;
        System.out.println(test.name);
        System.out.println(test.num);
        MatrixEnum[] values = MatrixEnum.values();
        for (MatrixEnum value : values) {
            System.out.println(value.toString());
        }
    }
}
