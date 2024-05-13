//package com.ssafy.enjoytrip.global.security.jwt;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ssafy.enjoytrip.global.security.CustomMemberDetails;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.IOException;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * JWT 로직을 사용함에 따라 기본 폼 로그인의 UsernamePasswordAuthenticationFilter 사용 불가
// * 따라서 직접 구현하여 게스트 자격의 Authentication 객체를 AuthenticationManager에 넘겨줘야함
// */
//@Slf4j
//public class LoginFilter extends UsernamePasswordAuthenticationFilter {
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//        // 로그인 처리 URL을 /api/member/login으로 설정
//        setFilterProcessesUrl("api/member/login");
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        Map<String, String> credentials = null;
//        try {
//            credentials = objectMapper.readValue(request.getInputStream(), Map.class);
//            String username = credentials.get("loginId");
//            String password = credentials.get("loginPw");
//
//            // 게스트 자격의 Authenticaiton 객체
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password, null);
//
//            // 매니저를 통해 검증
//            return authenticationManager.authenticate(authenticationToken);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // 성공시 반환할 JWT (실제론 SuccessHandler를 호출하는 로직이지만 JWT를 사용중이므로)
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//        CustomMemberDetails customAccountDetails = (CustomMemberDetails) authentication.getPrincipal();
//        String userId = customAccountDetails.getUsername();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority auth = iterator.next();
//
//        String role = auth.getAuthority();
//
//        // 표준 방식이라 지켜줘야함
//
//    }
//
//    // 실패시 반환할 JWT
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        response.setStatus(401);
//    }
//}
