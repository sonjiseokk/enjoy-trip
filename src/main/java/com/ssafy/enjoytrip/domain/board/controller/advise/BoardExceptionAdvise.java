package com.ssafy.enjoytrip.domain.board.controller.advise;

import com.ssafy.enjoytrip.domain.board.controller.BoardRestController;
import com.ssafy.enjoytrip.global.Result;
import com.ssafy.enjoytrip.global.exception.NotFoundArticleException;
import lombok.Builder;
import lombok.Data;
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
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new Result<>(false, HttpStatus.UNAUTHORIZED.value(), "아아아"));
    }

    @Data
    @Builder
    static class Result<T> {
        boolean success;
        int status;
        T data;
    }
}
