package com.ssafy.enjoytrip.domain.trip.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContentTypeDto {
    private int contentTypeId;
    private String typeName;

    @Builder
    public ContentTypeDto(final int contentTypeId, final String typeName) {
        this.contentTypeId = contentTypeId;
        this.typeName = typeName;
    }
}
