package com.douyu.ocean.demo.core.demo012Options;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserEntity {

    private ExamEntity examEntity;
    private List<ExamEntity> examEntityList = Lists.newArrayList();
}