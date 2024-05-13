package com.ssafy.enjoytrip.api.moderation.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModerationAPIResponse {
    private Map<String, AttributeScores> attributeScores;

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AttributeScores {
        @JsonProperty("spanScores")
        private List<SpanScore> spanScores;
        @JsonProperty("summaryScore")
        private Score summaryScore;
    }

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SpanScore {
        private int begin;
        private int end;
        private Score score;
    }

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Score {
        private double value;
        private String type;
    }
}
