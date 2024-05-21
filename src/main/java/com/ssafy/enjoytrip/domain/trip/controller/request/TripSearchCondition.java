package com.ssafy.enjoytrip.domain.trip.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TripSearchCondition {
	private String keyword;
	private int contentTypeId = 0;
	private int sidoCode = 0;
	private int gugunCode = 0;

	@Builder
	public TripSearchCondition(final String keyword, final int contentTypeId, final int sidoCode, final int gugunCode) {
		this.keyword = keyword;
		this.contentTypeId = contentTypeId;
		this.sidoCode = sidoCode;
		this.gugunCode = gugunCode;
	}
}
