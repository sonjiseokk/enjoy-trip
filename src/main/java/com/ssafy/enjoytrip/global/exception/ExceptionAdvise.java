package com.ssafy.enjoytrip.global.exception;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvise {
    @ExceptionHandler(NotFoundException.class)
    public String notFound(NotFoundException e, Model model) {
        model.addAttribute("errmsg", e.getMessage());
        return "error";
    }
}
