package com.ssafy.enjoytrip.domain.trip.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AttractionDescDto {
	private int contentId;
	private String overview;
	
	public AttractionDescDto(int contentId, String overview) {
		this.contentId = contentId;
		this.overview = overview;
	}

	@Override
	public String toString() {
		return "TripDescriptionDto [descId=" + contentId + ", overview=" + overview + "]";
	}

}
