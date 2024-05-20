package com.ssafy.enjoytrip.global.util;


import java.security.SecureRandom;

public class RandomGenerator {
    public static String generateCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    public static int generateRandomIndex(int bound) {
        SecureRandom random = new SecureRandom();
        return random.nextInt(bound);
    }
}
