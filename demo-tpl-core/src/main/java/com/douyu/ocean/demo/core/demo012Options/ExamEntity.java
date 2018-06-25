package com.douyu.ocean.demo.core.demo012Options;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExamEntity {

    private ScoreEntity scoreEntity;
    private List<ScoreEntity> scoreEntityList = null;

}