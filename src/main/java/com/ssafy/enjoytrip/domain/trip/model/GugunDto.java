package com.ssafy.enjoytrip.domain.trip.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GugunDto {
	private int gugunCode;
	private String gugunName;
	private int sidoCode;
	
	public GugunDto(int gugunCode, String gugunName, int sidoCode) {
		this.gugunCode = gugunCode;
		this.gugunName = gugunName;
		this.sidoCode = sidoCode;
	}
	
	@Override
	public String toString() {
		return "GugunDto [gugunCode=" + gugunCode + ", gugunName=" + gugunName + ", sidoCode=" + sidoCode + "]";
	}

}
