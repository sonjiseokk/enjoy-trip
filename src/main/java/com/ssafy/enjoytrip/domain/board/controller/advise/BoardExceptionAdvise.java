package com.ssafy.enjoytrip.domain.board.controller.advise;

import com.ssafy.enjoytrip.global.Result;
import com.ssafy.enjoytrip.global.exception.NotFoundArticleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice("com.ssafy.enjoytrip.domain.board")
public class BoardExceptionAdvise {
    @ExceptionHandler(NotFoundArticleException.class)
    public ResponseEntity<?> exception(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Result.builder()
                        .success(false)
                        .status(HttpStatus.NOT_FOUND.value())
                        .data(e.getMessage())
                        .build());
    }
    @ExceptionHandler(Exception.class)
    public String exception(Exception e, Model model) {
        log.error(e.getMessage());
        return "error";
    }
}
