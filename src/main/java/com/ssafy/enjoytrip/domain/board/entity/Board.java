package com.ssafy.enjoytrip.domain.board.entity;

public class Board {
	private int boardId;
	private String username;
	private String subject;
	private String content;
	private int hit;
	private String createDate;
	
	public Board() {	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Board [boardId=" + boardId + ", username=" + username + ", subject=" + subject + ", content=" + content
				+ ", hit=" + hit + ", createDate=" + createDate + "]";
	}


	
	
	
	
}
