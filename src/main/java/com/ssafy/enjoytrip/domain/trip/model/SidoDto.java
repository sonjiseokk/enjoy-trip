package com.ssafy.enjoytrip.domain.trip.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SidoDto {
	private int sidoCode;
	private String sidoName;
		
	public SidoDto(int sidoCode, String sidoName) {
		this.sidoCode = sidoCode;
		this.sidoName = sidoName;
	}
	
	@Override
	public String toString() {
		return "SidoDto [sidoCode=" + sidoCode + ", sidoName=" + sidoName + "]";
	}

}
