package com.ssafy.enjoytrip.domain.member.entity;

public class Member {
	private String userId;
	private String userPassword;
	private String userName;
	private String userEmail;
	
	public Member() {	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPw) {
		this.userPassword = userPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public String toString() {
		return "Member [userId=" + userId + ", userPw=" + userPassword + ", userName=" + userName + ", userEmail="
				+ userEmail + "]";
	}
	
	
}
