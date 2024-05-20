package com.ssafy.enjoytrip.domain.member.controller.request;

import lombok.Getter;

@Getter
public class PasswordFindRequest {
    private String userId;
    private String userName;

    public PasswordFindRequest(final String userId, final String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
