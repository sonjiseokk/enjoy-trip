package com.ssafy.enjoytrip.domain.like.controller.advise;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice("com.ssafy.enjoytrip.domain.like")
public class LikeAdvise {
	@ExceptionHandler(Exception.class)
    public String notFound(Exception e, Model model) {
        log.error(e.getMessage());
        return "error";
    }
}
