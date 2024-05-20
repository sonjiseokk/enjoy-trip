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

    @GetMapping("/most")
    public ResponseEntity<?> getMostTen(HttpServletRequest request) throws Exception {

        String userId = getUserId(request);
        List<AttractionInfoDto> myLikes = likeService.listLike(userId);
        AttractionInfoDto randomAttraction = embeddingService.pickRandomAttraction(myLikes);
        List<SimilarDto> mostFive = embeddingService.mostTen(randomAttraction);

        List<SubResult> result = new ArrayList<>();
        for (SimilarDto similarDto : mostFive) {
            result.add(new SubResult(similarDto.getSimilarity(), attractionInfoService.findAttractionInfo(similarDto.getTitle())));
        }

        SelectedInfo selectedInfo = new SelectedInfo(randomAttraction, result);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(true, HttpStatus.OK.value(), selectedInfo));
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
    @AllArgsConstructor
    static class SelectedInfo {
        AttractionInfoDto dto;
        List<SubResult> recommendations;
    }

    @Data
    @Builder
    @AllArgsConstructor
    static class SubResult {
        double similarity;
        AttractionInfoDto info;
    }

}
