package com.ssafy.enjoytrip.api.embedding.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimilarDto {
    private String title;
    private double similarity;

    public SimilarDto(final String title, final double similarity) {
        this.title = title;
        this.similarity = similarity;
    }
}
