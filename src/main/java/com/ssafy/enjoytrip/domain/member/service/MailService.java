package com.ssafy.enjoytrip.domain.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;
    private final ResourceLoader resourceLoader;

    public void sendEmail(String toEmail, String title, String text) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        try {
            helper.setTo(toEmail);
            helper.setSubject(title);
            helper.setText(text, true); // HTML 내용
            emailSender.send(message);
        } catch (MessagingException e) {
            log.debug("MailService.sendEmail 예외 발생 toEmail: {}, title: {}, text: {}", toEmail, title, text);
            throw new Exception("이메일 전송에 실패했습니다.");
        }
    }

    public String buildVerificationEmail(String code) {
        try {
            Resource resource = resourceLoader.getResource("classpath:templates/verification-email.html");
            String content = Files.readString(Path.of(resource.getURI()), StandardCharsets.UTF_8);
            return content.replace("{{code}}", code);
        } catch (Exception e) {
            log.error("이메일 템플릿 로딩 실패", e);
            throw new RuntimeException("이메일 템플릿 로딩에 실패했습니다.");
        }
    }
}
