package com.ssafy.enjoytrip.domain.member.controller;

import com.ssafy.enjoytrip.domain.member.service.VerificationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/api/member/email")
@RequiredArgsConstructor
public class MemberEmailController {
    private final VerificationService verificationService;

    @PostMapping("/send")
    public ResponseEntity<?> sendVerificationCode(@RequestParam String email) {
        try {
            verificationService.sendVerificationCode(email);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true, HttpStatus.OK.value(), "이메일 전송에 성공하였습니다."));
        } catch (Exception e) {
            e.fillInStackTrace();
            return ResponseEntity.status(500)
                    .body(new Result<>(true, HttpStatus.OK.value(), "이메일 전송에 실패했습니다."));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestParam String email, @RequestParam String code) {
        if (verificationService.verifyCode(email, code)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true, HttpStatus.OK.value(), "이메일 인증에 성공하였습니다."));
        } else {
            return ResponseEntity.status(400)
                    .body(new Result<>(true, HttpStatus.OK.value(), "이메일 인증에 실패하였습니다."));
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        boolean success;
        int code;
        T data;
    }
}
