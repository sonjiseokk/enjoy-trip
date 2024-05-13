package com.ssafy.enjoytrip.global.security.jwt;

import com.ssafy.enjoytrip.domain.member.model.JwtDto;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private SecretKey secretKey;

    // 프로퍼티의 키를 사용하여 시크릿 키를 만들었다.
    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUserId(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload().
                get("role", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public String createAccessToken(String userId, String role, Date createdTime) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("role", role)
                // 현재 발행 시간
                .issuedAt(createdTime)
                // 현재 발행 시간 + 어느 정도 지나야 만료될지
                .expiration(new Date(createdTime.getTime() + 1000L * 60 * 60 * 2)) // 2시간
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String userId, String role, Date createdTime) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("role", role)
                // 현재 발행 시간
                .issuedAt(createdTime)
                // 현재 발행 시간 + 어느 정도 지나야 만료될지
                .expiration(new Date(createdTime.getTime() + 1000L * 60 * 60 * 24 * 7)) // 2시간
                .signWith(secretKey)
                .compact();
    }

    public JwtDto getJwtDto(final Date createdTime, final MemberDto findMemberDto) {
        String accessToken = createAccessToken(
                findMemberDto.getUserId(), findMemberDto.getRole(),
                createdTime);
        String refreshToken = createRefreshToken(findMemberDto.getUserId(), findMemberDto.getRole(), createdTime);

        return JwtDto.builder()
                .createdTime(createdTime)
                .expiredTime(new Date(createdTime.getTime() + 1000L * 60 * 60 * 2))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
