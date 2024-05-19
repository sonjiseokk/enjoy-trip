package com.ssafy.enjoytrip.domain.board.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class BannedBoardDto {
	private int boardId;
	private String subject;
	private String content;
	private String createDate;

	// 외래키라도 DTO 필드에 넣어두는 게 일반적이라고 함
	private String userId;
	private int viewCount;
	private int boardTypeId;
	private int contentId;
	
	// 각 속성들
	@Setter
	private double toxicity = 0.0000000;
	@Setter
	private double severeToxicity = 0.0000000;
	@Setter
	private double identityAttack = 0.0000000;
	@Setter
	private double insult = 0.0000000;
	@Setter
	private double profanity = 0.0000000;
	@Setter
	private double threat = 0.0000000;
	
	
	@Builder
	public BannedBoardDto(int boardId, String subject, String content, String createDate, String userId, int viewCount,
			int boardTypeId, int contentId, double toxicity, double severeToxicity, double identityAttack,
			double insult, double profanity, double threat) {
		super();
		this.boardId = boardId;
		this.subject = subject;
		this.content = content;
		this.createDate = createDate;
		this.userId = userId;
		this.viewCount = viewCount;
		this.boardTypeId = boardTypeId;
		this.contentId = contentId;
		this.toxicity = toxicity;
		this.severeToxicity = severeToxicity;
		this.identityAttack = identityAttack;
		this.insult = insult;
		this.profanity = profanity;
		this.threat = threat;
	}

	@Builder
	public BannedBoardDto(BoardDto boardDto) {
		super();
		this.boardId = boardDto.getBoardId();
		this.subject = boardDto.getSubject();
		this.content = boardDto.getContent();
		this.createDate = boardDto.getCreateDate();
		this.userId = boardDto.getUserId();
		this.viewCount = boardDto.getViewCount();
		this.boardTypeId = boardDto.getBoardTypeId();
		this.contentId = boardDto.getContentId();
	}
}
