package com.ssafy.enjoytrip.domain.member.controller;

import com.ssafy.enjoytrip.domain.member.model.JwtDto;
import com.ssafy.enjoytrip.global.security.jwt.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class MemberAuthController {
    private final JwtUtil jwtUtil;

    @PostMapping("/refresh")
    public ResponseEntity<?> getAccessToken(@RequestBody JwtDto jwtDto) throws Exception {
        log.info("jwt dto = {}", jwtDto.toString());
        if (!jwtUtil.isExpired(jwtDto.getAccessToken())) {
            Map<String, String> result = new HashMap<>();

            Date createdTime = new Date(System.currentTimeMillis());
            String accessToken = jwtUtil.createAccessToken(jwtDto.getUserId(),
                    jwtDto.getRole(), createdTime, jwtUtil.calculateAccessExpireTime(createdTime));

            result.put("token", accessToken);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(result);
        }

        // refresh 까지 만료
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("리프레시 토큰이 만료되었습니다.");
    }

    @Data
    @AllArgsConstructor
    static class Result {
        private String token;
    }
}
