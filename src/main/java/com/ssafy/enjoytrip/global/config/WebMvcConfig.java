package com.ssafy.enjoytrip.global.config;

import com.ssafy.enjoytrip.global.interceptor.BoardInterceptor;
import com.ssafy.enjoytrip.global.interceptor.MemberInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new MemberInterceptor())
                .addPathPatterns("/member/**","/api/member/**")
                .excludePathPatterns("/member/login", "/member/join")
                .excludePathPatterns("/member/update/password/find")
                .excludePathPatterns("/api/member/login", "/api/member/join");

        registry.addInterceptor(new BoardInterceptor())
                .addPathPatterns("/board/write")
                .addPathPatterns("/api/board/write");
    }

}
