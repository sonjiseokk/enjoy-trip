package com.ssafy.enjoytrip.domain.member.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VerificationDto {
    private String code;
    private String password;

    @Builder
    public VerificationDto(final String code, final String password) {
        this.code = code;
        this.password = password;
    }
}
