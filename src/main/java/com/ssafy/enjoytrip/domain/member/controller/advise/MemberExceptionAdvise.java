package com.ssafy.enjoytrip.domain.member.controller.advise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice("com.ssafy.enjoytrip.domain.member")
public class MemberExceptionAdvise {
    @ExceptionHandler(Exception.class)
    public String notFound(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return "error";
    }
}
