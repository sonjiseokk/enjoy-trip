package com.ssafy.enjoytrip.domain.member.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateMemberDto {
    private String userName;
    private String userPassword;
    private String userEmail;

    @Builder
    public UpdateMemberDto(final String userName, final String userPassword, final String userEmail) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }
}
