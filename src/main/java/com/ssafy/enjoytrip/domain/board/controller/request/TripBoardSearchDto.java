package com.ssafy.enjoytrip.domain.board.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripBoardSearchDto {
	private int boardTypeId;
	private int contentId;
	public TripBoardSearchDto(int boardTypeId, int contentId) {
		super();
		this.boardTypeId = boardTypeId;
		this.contentId = contentId;
	}
}
