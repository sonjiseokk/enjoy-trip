package com.ssafy.enjoytrip.api.datago.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TripItemDto {
    @JsonProperty("contentid")
    private int contentId;

    @JsonProperty("contenttypeid")
    private int contentTypeId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("addr1")
    private String address;

    @JsonProperty("firstimage")
    private String thumbnailImage;

    @JsonProperty("mapy")
    private double latitude;

    @JsonProperty("mapx")
    private double longitude;

    @JsonProperty("mlevel")
    private String mLevel;

    @JsonProperty("areacode")
    private int sidoCode;

    @JsonProperty("sigungucode")
    private int gugunCode;
}

