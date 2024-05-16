package com.ssafy.enjoytrip.domain.member.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Getter
@NoArgsConstructor
@ToString
public class JwtDto {
    private String userId;
    private String userName;
    private String userEmail;
    private String role;
    private Date createdTime;
    private Date expiredTime;
    private String accessToken;
    private String refreshToken;

    @Builder

    public JwtDto(final String userId, final String userName, final String userEmail, final String role, final Date createdTime, final Date expiredTime, final String accessToken, final String refreshToken) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.role = role;
        this.createdTime = createdTime;
        this.expiredTime = expiredTime;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
