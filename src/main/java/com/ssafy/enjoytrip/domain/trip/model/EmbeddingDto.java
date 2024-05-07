package com.ssafy.enjoytrip.domain.trip.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmbeddingDto {
    private int embeddingId;
    private String embeddingName;
    private String vector;

    public EmbeddingDto(final int embeddingId, final String embeddingName, final String vector) {
        this.embeddingId = embeddingId;
        this.embeddingName = embeddingName;
        this.vector = vector;
    }
}
