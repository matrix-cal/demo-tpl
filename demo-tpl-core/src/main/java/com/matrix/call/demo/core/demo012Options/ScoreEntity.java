package com.matrix.call.demo.core.demo012Options;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScoreEntity {

    private List<Integer> itemScore;

    private int articalScore;

    public ScoreEntity(int articalScore) {
        this.articalScore = articalScore;
    }
}