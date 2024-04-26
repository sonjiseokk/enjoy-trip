package com.ssafy.enjoytrip.domain.trip.controller.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice("com.ssafy.enjoytrip.domain.trip")
public class TripExceptionAdvise {
	@ExceptionHandler(Exception.class)
    public String notFound(Exception e, Model model) {
        log.error(e.getMessage());
        return "error";
    }
}
