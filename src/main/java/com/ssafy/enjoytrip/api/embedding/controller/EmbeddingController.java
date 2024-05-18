package com.ssafy.enjoytrip.api.embedding.controller;

import com.ssafy.enjoytrip.api.embedding.model.SimilarDto;
import com.ssafy.enjoytrip.api.embedding.service.EmbeddingService;
import com.ssafy.enjoytrip.domain.like.service.LikeService;
import com.ssafy.enjoytrip.domain.trip.model.AttractionInfoDto;
import com.ssafy.enjoytrip.domain.trip.service.AttractionInfoService;
import com.ssafy.enjoytrip.global.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/embed")
@RequiredArgsConstructor
public class EmbeddingController {
    private final JwtUtil jwtUtil;
    private final LikeService likeService;
    private final EmbeddingService embeddingService;
    private final AttractionInfoService attractionInfoService;

    @GetMapping("/most5")
    public ResponseEntity<?> getMost5(HttpServletRequest request) throws Exception {
        // TODO : like 기능 구현하고 올게요
        String userId = getUserId(request);
        List<AttractionInfoDto> myLikes = likeService.listLike(userId);

        List<SimilarDto> mostFive = embeddingService.myMostFive(myLikes);

        List<SubResult> result = new ArrayList<>();
        for (SimilarDto similarDto : mostFive) {
            result.add(new SubResult(similarDto.getSimilarity(), attractionInfoService.findAttractionInfo(similarDto.getTitle())));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(true, HttpStatus.OK.value(), result));
    }

    private String getUserId(final HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token = authorization.split(" ")[1];
        return jwtUtil.getUserId(token);
    }

    @Data
    @Builder
    static class Result<T> {
        boolean success;
        int status;
        T data;
    }

    @Data
    @Builder
    @AllArgsConstructor
    static class SubResult<T> {
        double similarity;
        AttractionInfoDto info;
    }

}
