package com.ssafy.enjoytrip.api.embedding.model;

import lombok.Builder;
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
    private String sourceTitle;  // 추천된 찜 목록의 title

    @Builder
    public SimilarDto(final String title, final double similarity, final String sourceTitle) {
        this.title = title;
        this.similarity = similarity;
        this.sourceTitle = sourceTitle;
    }
}

