package com.ssafy.enjoytrip.domain.board.model;

import lombok.Getter;

@Getter
public class WriteResponse {
	// -1이면 차단됨, 1이면 성공적으로 작성됨
	private int code;
	private String message;
	
	public WriteResponse(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
}
