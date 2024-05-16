package com.ssafy.enjoytrip.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import com.ssafy.enjoytrip.global.security.CustomMemberDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 프론트에서 전달받은 JWT 키를 검증하는 필터
 */
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //request에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        String path = request.getRequestURI();

        // /api/member/join 경로인 경우 필터를 적용하지 않고 바로 다음 필터로 넘깁니다.

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token null");
            Result<?> result = Result.builder()
                    .success(false)
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .data("JWT 토큰이 없습니다.")
                    .build();

            // 응답을 JSON 형태로 설정
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정

            // ObjectMapper를 사용해 DTO를 JSON 문자열로 변환 후 응답에 쓰기
            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(result);
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        System.out.println("authorization now");

        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            System.out.println("token expired");
            filterChain.doFilter(request, response);
            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //토큰에서 email, role 획득
        String userId = jwtUtil.getUserId(token);
        String role = jwtUtil.getRole(token);


        // 여기서 정확한 유저 데이터를 가지면 좋겠지만, 인증 절차마다 DB 조회를 한다면 비효율적
        // 따라서 여기선 그냥 username, role만 유효한 객체를 생성해서 details에 넣어주려고 함
        MemberDto memberDto = MemberDto.builder()
                .userId(userId)
                .build();
        memberDto.setRole(role);
        //UserDetails에 회원 정보 객체 담기
        CustomMemberDetails customMemberDetails = new CustomMemberDetails(memberDto);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customMemberDetails, null, customMemberDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    @Data
    @Builder
    static class Result<T> {
        boolean success;
        int status;
        T data;
    }
}