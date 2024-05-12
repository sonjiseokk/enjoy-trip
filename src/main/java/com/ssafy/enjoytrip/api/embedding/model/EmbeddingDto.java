package com.ssafy.enjoytrip.api.embedding.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmbeddingDto {
    private int embeddingId;
    private String embeddingName;
    private double[] vector;

    @Builder
    public EmbeddingDto(final int embeddingId, final String embeddingName, final double[] vector) {
        this.embeddingId = embeddingId;
        this.embeddingName = embeddingName;
        this.vector = vector;
    }
}
