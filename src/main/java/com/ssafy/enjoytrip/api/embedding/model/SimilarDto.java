package com.ssafy.enjoytrip.api.embedding.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimilarDto {
    private int index;
    private double similarity;

    public SimilarDto(final int index, final double similarity) {
        this.index = index;
        this.similarity = similarity;
    }
}
