package com.ssafy.enjoytrip.domain.member.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginMemberDto {
    private String loginId;
    private String loginPw;

    @Builder
    public LoginMemberDto(final String loginId, final String loginPw) {
        this.loginId = loginId;
        this.loginPw = loginPw;
    }
}
