package com.ssafy.enjoytrip.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static void logic(String saveId, HttpServletRequest request, HttpServletResponse response) {
        if ("ok".equals(saveId)) { // 아이디 저장을 체크 했다면.
            Cookie cookie = new Cookie("ssafy_id", saveId);
            cookie.setPath(request.getContextPath());
            cookie.setMaxAge(60 * 60 * 24 * 365);
            response.addCookie(cookie);
        } else { // 아이디 저장을 해제 했다면.
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("ssafy_id".equals(cookie.getName())) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                        break;
                    }
                }
            }
        }
    }
}
