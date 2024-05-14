package com.ssafy.enjoytrip.domain.trip.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class TripDto {
	private int contentId;
	private String title;
	private String address;
	private String thumnailImage;
	private int readcount;
	private double latitude;
	private double longitude;
	private String mlevel;
	// 외래키
	private int gugunCode;
	private int sidoCode;
	@Setter
	private int contentTypeId;

	public TripDto(final int contentId, final String title, final String address,
				   final String thumnailImage, final int readcount,
				   final double latitude, final double longitude,
				   final String mlevel, final int gugunCode,
				   final int sidoCode, final int contentTypeId) {
		this.contentId = contentId;
		this.title = title;
		this.address = address;
		this.thumnailImage = thumnailImage;
		this.readcount = readcount;
		this.latitude = latitude;
		this.longitude = longitude;
		this.mlevel = mlevel;
		this.gugunCode = gugunCode;
		this.sidoCode = sidoCode;
		this.contentTypeId = contentTypeId;
	}
}
