package com.ssafy.enjoytrip.global.util;

import java.util.HashMap;
import java.util.Map;

public class HtmlUtil {
    private static final Map<String, String> htmlEscapes = new HashMap<>();

    static {
        htmlEscapes.put("&", "&amp;");
        htmlEscapes.put("<", "&lt;");
        htmlEscapes.put(">", "&gt;");
        htmlEscapes.put("\"", "&quot;");
        htmlEscapes.put("'", "&#39;");
        htmlEscapes.put("\n", "<br/>"); // 개행 문자 변환
    }

    public static String escapeHtml(String content) {
        if (content == null) {
            return null;
        }

        StringBuilder escapedContent = new StringBuilder();
        for (char c : content.toCharArray()) {
            String escape = htmlEscapes.get(String.valueOf(c));
            if (escape != null) {
                escapedContent.append(escape);
            } else {
                escapedContent.append(c);
            }
        }
        return escapedContent.toString();
    }
}