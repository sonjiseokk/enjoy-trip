package com.ssafy.enjoytrip.domain.board.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UpdateBoardDto {
	private int boardId;
    private String subject;
	@Setter
    private String content;

	@Builder
	public UpdateBoardDto(final int boardId, final String subject, final String content) {
		this.boardId = boardId;
		this.subject = subject;
		this.content = content;
	}
}
