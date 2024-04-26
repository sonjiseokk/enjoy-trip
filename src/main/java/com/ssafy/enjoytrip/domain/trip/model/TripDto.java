package com.ssafy.enjoytrip.domain.trip.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TripDto {
	private int contentId;
	private int contentTypeId;
	private String title;
	private String address;
	private String thumnailImage;
	private int readcount;
	private int sidoCode;
	private int gugunCode;
	private double latitude;
	private double longitude;
	private String mlevel;
	
	public TripDto(int contentId, int contentTypeId, String title, String addr, String thumnailImage, int readcount,
			int sidoCode, int gugunCode, double latitude, double longitude, String mlevel) {
		this.contentId = contentId;
		this.contentTypeId = contentTypeId;
		this.title = title;
		this.address = addr;
		this.thumnailImage = thumnailImage;
		this.readcount = readcount;
		this.sidoCode = sidoCode;
		this.gugunCode = gugunCode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.mlevel = mlevel;
	}
}
