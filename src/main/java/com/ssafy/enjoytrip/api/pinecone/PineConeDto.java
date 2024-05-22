package com.ssafy.enjoytrip.api.pinecone;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PineConeDto {
    private int contentId;
    private double similarity;

    @Builder
    public PineConeDto(final int contentId, final double similarity) {
        this.contentId = contentId;
        this.similarity = similarity;
    }
}
