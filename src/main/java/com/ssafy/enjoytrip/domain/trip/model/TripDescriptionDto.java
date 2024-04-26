package com.ssafy.enjoytrip.domain.trip.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TripDescriptionDto {
	private String descId;
	private String overview;
	
	public TripDescriptionDto(String descId, String overview) {
		this.descId = descId;
		this.overview = overview;
	}

	@Override
	public String toString() {
		return "TripDescriptionDto [descId=" + descId + ", overview=" + overview + "]";
	}

}
