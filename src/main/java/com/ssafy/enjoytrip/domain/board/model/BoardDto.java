package com.ssafy.enjoytrip.domain.board.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class BoardDto {
	private int boardId;
	private String subject;
	private String content;
	private String createDate;

	// 외래키라도 DTO 필드에 넣어두는 게 일반적이라고 함
	private String userId;
	private int viewCount;
	private int boardTypeId;

	@Builder
	public BoardDto(final int boardId, final String subject, final String content, final String createDate, final String userId, final int viewCount, final int boardTypeId) {
		this.boardId = boardId;
		this.subject = subject;
		this.content = content;
		this.createDate = createDate;
		this.userId = userId;
		this.viewCount = viewCount;
		this.boardTypeId = boardTypeId;
	}
}
