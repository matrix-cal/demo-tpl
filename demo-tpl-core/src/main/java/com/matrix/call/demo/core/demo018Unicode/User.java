package com.matrix.call.demo.core.demo018Unicode;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class User {
    private List<Child> childList;
    private String name;
}
