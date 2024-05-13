package com.ssafy.enjoytrip.domain.member.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class JwtDto {
    private Date createdTime;
    private Date expiredTime;
    private String accessToken;
    private String refreshToken;

    @Builder
    public JwtDto(final Date createdTime, final Date expiredTime, final String accessToken, final String refreshToken) {
        this.createdTime = createdTime;
        this.expiredTime = expiredTime;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
