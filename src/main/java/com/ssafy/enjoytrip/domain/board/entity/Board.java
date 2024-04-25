package com.ssafy.enjoytrip.domain.board.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Board {
	private int boardId;
	private String subject;
	private String content;
	private LocalDateTime createDate;

	@Builder
	public Board(final int boardId, final String subject, final String content, final LocalDateTime createDate) {
		this.boardId = boardId;
		this.subject = subject;
		this.content = content;
		this.createDate = createDate;
	}
}
