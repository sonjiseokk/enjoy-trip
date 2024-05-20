package com.ssafy.enjoytrip.global.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CodeGenerator {
    private static final int CODE_LENGTH = 6;

    public static String generateCode() {
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder(CODE_LENGTH);
            for (int i = 0; i < CODE_LENGTH; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Secure random instance not available", e);
        }
    }
}