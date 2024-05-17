package com.ssafy.enjoytrip.api.embedding.controller;

import com.ssafy.enjoytrip.api.embedding.model.SimilarDto;
import com.ssafy.enjoytrip.api.embedding.service.EmbeddingService;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/embed")
@RequiredArgsConstructor
public class EmbeddingController {
    private final EmbeddingService embeddingService;

    @GetMapping("/most5")
    public ResponseEntity<?> getMost5(@RequestParam String userId) throws Exception {
        // TODO : like 기능 구현하고 올게요
        List<SimilarDto> mostFive = embeddingService.getMostFive(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(true, HttpStatus.OK.value(), mostFive));
    }

    @Data
    @Builder
    static class Result<T> {
        boolean success;
        int status;
        T data;
    }

}
