package com.ssafy.enjoytrip.api.embedding.controller;

import com.ssafy.enjoytrip.api.embedding.model.SimilarDto;
import com.ssafy.enjoytrip.api.embedding.service.EmbeddingService;
import com.ssafy.enjoytrip.api.pinecone.PineConeDto;
import com.ssafy.enjoytrip.api.pinecone.service.PineconeService;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/embed")
@RequiredArgsConstructor
@CrossOrigin(value = "*")
public class EmbeddingController {
    private final JwtUtil jwtUtil;
    private final LikeService likeService;
    private final EmbeddingService embeddingService;
    private final AttractionInfoService attractionInfoService;
    private final PineconeService pineconeService;

    @GetMapping("/most")
    public ResponseEntity<?> getMostTen(HttpServletRequest request) throws Exception {

        String userId = getUserId(request);
        List<AttractionInfoDto> myLikes = likeService.listLike(userId);
        AttractionInfoDto randomAttraction = embeddingService.pickRandomAttraction(myLikes);
        List<SimilarDto> mostFive = embeddingService.mostTen(myLikes, randomAttraction);

        List<SubResult> result = new ArrayList<>();
        for (SimilarDto similarDto : mostFive) {
            result.add(new SubResult(similarDto.getSimilarity(), attractionInfoService.findAttractionInfo(similarDto.getTitle())));
        }

        SelectedInfo selectedInfo = new SelectedInfo(randomAttraction, result);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(true, HttpStatus.OK.value(), selectedInfo));
    }

    @GetMapping("/recommend")
    public ResponseEntity<?> recommend(@RequestParam int contentId,@RequestParam("number") int number) throws Exception {
        List<PineConeDto> pineConeDtos = pineconeService.compareVectors(number, contentId);
        List<SubResult> result = new ArrayList<>();
        // 0은 자기 자신, 이외의 3개를 뽑아냄
        for (int i = 1; i < number; i++) {
            PineConeDto pineConeDto = pineConeDtos.get(i);
            result.add(new SubResult(pineConeDto.getSimilarity(), attractionInfoService.findAttractionContentId(pineConeDto.getContentId())));
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
