package com.ssafy.enjoytrip.domain.member.repository;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class VerificationCodeStore {
    private final ConcurrentHashMap<String, VerificationCode> store = new ConcurrentHashMap<>();

    public void save(String email, String code, LocalDateTime expirationTime) {
        store.put(email, new VerificationCode(code, expirationTime));
    }

    public VerificationCode get(String email) {
        return store.get(email);
    }

    public void remove(String email) {
        store.remove(email);
    }

    @Getter
    public static class VerificationCode {
        private final String code;
        private final LocalDateTime expirationTime;

        public VerificationCode(String code, LocalDateTime expirationTime) {
            this.code = code;
            this.expirationTime = expirationTime;
        }

    }
}

