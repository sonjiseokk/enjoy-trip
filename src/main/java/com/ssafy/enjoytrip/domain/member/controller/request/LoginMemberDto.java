package com.ssafy.enjoytrip.domain.member.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class LoginMemberDto {
    private String loginId;
    private String loginPw;
}
