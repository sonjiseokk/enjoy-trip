package com.ssafy.enjoytrip.api.moderation.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ModerationResponse {
    private String attributeName;
    private double score;

    @Builder
    public ModerationResponse(final String attributeName, final double score) {
        this.attributeName = attributeName;
        this.score = score;
    }
}
