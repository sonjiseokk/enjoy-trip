package com.ssafy.enjoytrip.domain.member.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {
	private String userId;
	private String userName;
	private String userPassword;
	private String userEmail;

	@Builder
	public Member(final String userId, final String userName, final String userPassword, final String userEmail) {
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
	}

	public void encryptPassword(String cryptPw) {
		this.userPassword = cryptPw;
	}
}
