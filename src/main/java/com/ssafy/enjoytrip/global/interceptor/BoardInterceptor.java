package com.ssafy.enjoytrip.global.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

public class BoardInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        MemberDto userinfo = (MemberDto) request.getSession().getAttribute("userinfo");
        if (userinfo == null) {
            // Set the response status and content type
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 status code
            response.setContentType("application/json");

            // Create the response body
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("status", 401);
            responseData.put("data", "User not authenticated");

            // Convert map to JSON
            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(responseData);

            // Write the JSON response to output
            response.getWriter().write(jsonResponse);

            // Return false to indicate request handling should not proceed
            return false;
        }
        return true;
    }
}
