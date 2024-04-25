package com.ssafy.enjoytrip.domain.board.controller.advise;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice("com.ssafy.enjoytrip.domain.board")
public class BoardExceptionAdvise {
    @ExceptionHandler(Exception.class)
    public String notFound(Exception e, Model model) {
        log.error(e.getMessage());
        return "error";
    }
}
