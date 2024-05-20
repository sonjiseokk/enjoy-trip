package com.ssafy.enjoytrip.domain.trip.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TripSearchCondition {
	private int contentTypeId = 0;
	private int sidoCode = 0;
	private int gugunCode = 0;
	
	public TripSearchCondition(int contentTypeId, int sidoCode, int gugunCode) {
		this.contentTypeId = contentTypeId;
		this.sidoCode = sidoCode;
		this.gugunCode = gugunCode;
	}

	@Override
	public String toString() {
		return "TripSearchCondition [contentTypeId=" + contentTypeId + ", sidoCode=" + sidoCode + ", gugunCode="
				+ gugunCode + "]";
	}
	
	
}
