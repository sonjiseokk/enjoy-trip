package com.ssafy.enjoytrip.global.security.jwt;

import com.ssafy.enjoytrip.domain.member.model.JwtDto;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.Date;

@Component
@Slf4j
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
                .get("userId", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload().
                get("role", String.class);
    }

    public Boolean isExpired(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("JWT is expired: " + e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            System.out.println("Malformed JWT: " + e.getMessage());
            return false;
        }catch (Exception e) {
            System.out.println("Error parsing JWT: " + e.getMessage());
            return false;
        }
    }

    public String createAccessToken(String userId, String role, Date createdTime, final Date accessExpireTime) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("role", role)
                // 현재 발행 시간
                .issuedAt(createdTime)
                // 현재 발행 시간 + 어느 정도 지나야 만료될지
                .expiration(accessExpireTime)
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String userId, String role, Date createdTime, final Date refreshExpireTime) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("role", role)
                // 현재 발행 시간
                .issuedAt(createdTime)
                // 현재 발행 시간 + 어느 정도 지나야 만료될지
                .expiration(refreshExpireTime)
                .signWith(secretKey)
                .compact();
    }

    public JwtDto getJwtDto(final Date createdTime, final MemberDto findMemberDto) {
        Date accessExpireTime = calculateAccessExpireTime(createdTime);
        Date refreshExpireTime = new Date(createdTime.getTime() + 1000L * 60 * 60 * 24 * 30); // 30일
        log.info("refresh = {}",refreshExpireTime);
        String accessToken = createAccessToken(
                findMemberDto.getUserId(), findMemberDto.getRole(),
                createdTime, accessExpireTime);
        String refreshToken = createRefreshToken(findMemberDto.getUserId(), findMemberDto.getRole(), createdTime, refreshExpireTime);


        return JwtDto.builder()
                .userId(findMemberDto.getUserId())
                .userName(findMemberDto.getUserName())
                .userEmail(findMemberDto.getUserEmail())
                .role(findMemberDto.getRole())
                .createdTime(createdTime)
                .expiredTime(accessExpireTime)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Date calculateAccessExpireTime(Date now) {
        return new Date(now.getTime() + 1000L * 60 * 60 * 24 * 7);
    }
}
