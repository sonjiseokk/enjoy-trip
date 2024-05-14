package com.ssafy.enjoytrip.api.embedding.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmbeddingDto {
    private int contentId;
    private String embeddingName;
    private double[] vector;

    @Builder
    public EmbeddingDto(final int contentId, final String embeddingName, final double[] vector) {
        this.contentId = contentId;
        this.embeddingName = embeddingName;
        this.vector = vector;
    }
}
