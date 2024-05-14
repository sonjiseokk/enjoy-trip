package com.ssafy.enjoytrip.domain.board.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateBoardDto {
    private String subject;
    private String content;

	@Builder
	public UpdateBoardDto(final String subject, final String content) {
		this.subject = subject;
		this.content = content;
	}
}
