package com.ssafy.enjoytrip.domain.member.controller;

import com.ssafy.enjoytrip.domain.member.controller.request.VerificationDto;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import com.ssafy.enjoytrip.domain.member.service.MemberService;
import com.ssafy.enjoytrip.domain.member.service.VerificationService;
import com.ssafy.enjoytrip.global.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtUtil jwtUtil;
    private final VerificationService verificationService;
    private final MemberService memberService;

    @PostMapping("/send")
    public ResponseEntity<?> sendVerificationCode(HttpServletRequest request) throws Exception {
        String userId = getUserId(request);
        MemberDto member = memberService.findMember(userId);
        try {
            verificationService.sendVerificationCode(member.getUserEmail());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true, HttpStatus.OK.value(), String.format("이메일 전송에 성공하였습니다.\n%s에서 확인하세요", member.getUserEmail())));
        } catch (Exception e) {
            e.fillInStackTrace();
            return ResponseEntity.status(500)
                    .body(new Result<>(true, HttpStatus.OK.value(), String.format("이메일 전송에 실패했습니다.\n%s에서 확인하세요", member.getUserEmail())));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(HttpServletRequest request, @RequestBody VerificationDto verificationDto) throws Exception {
        String userId = getUserId(request);
        MemberDto member = memberService.findMember(userId);
        if (verificationService.verifyCode(member.getUserEmail(), verificationDto.getCode())) {
            memberService.updateMemberPassword(userId, verificationDto.getPassword());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true, HttpStatus.OK.value(), "성공적으로 비밀번호가 변경되었습니다.\n다시 로그인해주세요!"));
        } else {
            return ResponseEntity.status(400)
                    .body(new Result<>(true, HttpStatus.OK.value(), "이메일 인증에 실패하였습니다."));
        }
    }

    private String getUserId(final HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token = authorization.split(" ")[1];
        return jwtUtil.getUserId(token);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        boolean success;
        int code;
        T data;
    }
}
