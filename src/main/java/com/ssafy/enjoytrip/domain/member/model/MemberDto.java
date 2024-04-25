package com.ssafy.enjoytrip.domain.member.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDto {
	private String userId;
	private String userName;
	private String userPassword;
	private String userEmail;

	@Builder
	public MemberDto(final String userId, final String userName, final String userPassword, final String userEmail) {
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
	}

	public void encryptPassword(String cryptPw) {
		this.userPassword = cryptPw;
	}
}
