package com.ssafy.enjoytrip.api.embedding.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SimilarDto {
    @EqualsAndHashCode.Include
    private String title;
    private double similarity;

    public SimilarDto(final String title, final double similarity) {
        this.title = title;
        this.similarity = similarity;
    }

}
