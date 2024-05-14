package com.ssafy.enjoytrip.api.datago.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.enjoytrip.api.datago.model.TripItemDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
    @JsonProperty("response")
    private Response response;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {
        @JsonProperty("body")
        private Body body;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Body {
        @JsonProperty("items")
        private Items items;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Items {
        @JsonProperty("item")
        private List<TripItemDto> item;
    }
}

