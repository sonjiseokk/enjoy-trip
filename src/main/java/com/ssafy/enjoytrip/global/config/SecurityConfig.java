package com.ssafy.enjoytrip.global.config;


import com.ssafy.enjoytrip.global.security.jwt.JwtFilter;
import com.ssafy.enjoytrip.global.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/api/member/join",
                        "/api/member/login",
                        "/api/member/check/duplication/*",
                        "/api/auth/refresh",
                        "/api/board/list",
                        "/api/board/notice/**",
                        "/api/board/qna/**",
                        "/api/trip/**",
                        "/images/**",
                        "/api/member/email/**",
                        "/api/news/list/*",
                        "/test/**",
                        "/api/board/*/detail/**",
                        "/api/embed/recommend")
                .requestMatchers(PathRequest.toStaticResources()
                        .atCommonLocations());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager 를 반환하는 메소드
     * 현재 UsernamePasswordAuthenticationFilter 를 대체하는 LoginFilter가 AuthenticationManager 가 없는 상태
     * 따라서 주입해주기 위해 생성
     *
     * @param configuration 스프링 시큐리티 매니저, 프로바이저 등을 관리하는 객체
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CORS 세팅
        http.cors(cors -> cors
                .configurationSource(corsConfigurationSource())
        );
        // csrf disable -> JWT를 사용할 건데 JWT는 비동기라 csrf 공격 무의미
        http.csrf((auth) -> auth.disable());

        // 폼 로그인 방식 disable -> JWT를 사용할 것이기 때문
        http.formLogin((auth) -> auth.disable());

        // 베이직 로그인 disable -> 마찬가지로 JWT 사용 + 이건 보안상 이슈
        http.httpBasic((auth) -> auth.disable());

        // 로그인, 메인, 회원가입 제외하곤 전부 인증
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/api/member/join",
                                "/api/member/login",
                                "/api/member/check/duplication/*",
                                "/api/auth/refresh",
                                "/api/member/email/**",
                                "/api/board/*/detail/**",
                                "/images/**").permitAll()
                        .anyRequest().authenticated());

        // JWT는 무상태 프로토콜이기때문에 꼭 선언해줘야함
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        // 필터를 대체하는 메소드
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
