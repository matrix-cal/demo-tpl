package com.matrix.call.demo.core.demo018Unicode;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Toy {
    private String toyName;
    private String toyType;

    public Toy(String toyName, String toyType) {
        this.toyName = toyName;
        this.toyType = toyType;
    }

    public Toy() {
    }
}
