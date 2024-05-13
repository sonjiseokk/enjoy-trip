package com.ssafy.enjoytrip.api.moderation.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Setter
@Getter
public class ModerationAPIRequest {
    private Comment comment;
    private List<String> languages;
    private Map<String, Attribute> requestedAttributes;

    public ModerationAPIRequest(String text) {
        this.comment = new Comment(text);
        this.languages = List.of("ko");

        this.requestedAttributes = new HashMap<>();
        String[] attributes = {"TOXICITY", "SEVERE_TOXICITY", "IDENTITY_ATTACK", "INSULT", "PROFANITY", "THREAT"};
        Arrays.stream(attributes).forEach(attr -> requestedAttributes.put(attr, new Attribute(0.8)));
    }

    @Setter
    @Getter
    static class Comment {
        private String text;

        public Comment(String text) {
            this.text = text;
        }

    }

    @Setter
    @Getter
    static class Attribute {
        @JsonProperty("scoreThreshold")
        private double scoreThreshold;

        public Attribute(double scoreThreshold) {
            this.scoreThreshold = scoreThreshold;
        }

    }
}

