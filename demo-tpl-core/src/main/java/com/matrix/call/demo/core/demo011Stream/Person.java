package com.matrix.call.demo.core.demo011Stream;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
