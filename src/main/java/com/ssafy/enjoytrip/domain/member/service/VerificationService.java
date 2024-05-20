package com.ssafy.enjoytrip.domain.member.service;

import com.ssafy.enjoytrip.domain.member.repository.VerificationCodeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final MailService mailService;
    private final VerificationCodeStore codeStore = new VerificationCodeStore();

    public void sendVerificationCode(String email) throws Exception {
        String code = generateCode();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10); // 10분 유효
        codeStore.save(email, code, expirationTime);
        String emailContent = mailService.buildVerificationEmail(code);
        mailService.sendEmail(email, "Email Verification Code", emailContent);
    }

    private String generateCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    public boolean verifyCode(String email, String code) {
        VerificationCodeStore.VerificationCode storedCode = codeStore.get(email);
        if (storedCode != null && storedCode.getCode().equals(code) && storedCode.getExpirationTime().isAfter(LocalDateTime.now())) {
            codeStore.remove(email); // 검증 후 코드 제거
            return true;
        }
        return false;
    }
}

