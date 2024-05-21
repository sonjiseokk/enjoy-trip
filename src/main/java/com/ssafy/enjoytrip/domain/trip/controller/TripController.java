package com.ssafy.enjoytrip.domain.trip.controller;

import com.ssafy.enjoytrip.domain.trip.controller.request.TripSearchCondition;
import com.ssafy.enjoytrip.domain.trip.model.*;
import com.ssafy.enjoytrip.domain.trip.service.AttractionDescriptionService;
import com.ssafy.enjoytrip.domain.trip.service.AttractionInfoService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/trip")
@Slf4j
public class TripController {
    private final AttractionInfoService attractionInfoService;
    private final AttractionDescriptionService attractionDescriptionService;

    @PostMapping(value = "/search")
    public ResponseEntity<?> tripList(@RequestBody TripSearchCondition con) throws Exception {
        List<AttractionInfoDto> list = attractionInfoService.searchTrip(con);
        List<SubResult> result = new ArrayList<>();
        for (AttractionInfoDto dto : list) {
            AttractionDescDto descDto = attractionDescriptionService.findById(dto.getContentId());
            result.add(new SubResult<>(dto, descDto.getOverview()));
        }

        if (!list.isEmpty()) {
            return new ResponseEntity<>(new Result(HttpStatus.OK.value(), result), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(HttpStatus.NOT_FOUND.value(), "데이터를 찾을 수 없습니다."));
        }
    }

    @GetMapping(value = "/searchAll")
    public ResponseEntity<?> tripList(@RequestParam("keyword") String keyword) throws Exception {
        log.info("넘어온 키워드는 = {}",keyword);
        List<AttractionInfoDto> list = attractionInfoService.serachAll(keyword);
        List<SubResult> result = new ArrayList<>();
        for (AttractionInfoDto dto : list) {
            AttractionDescDto descDto = attractionDescriptionService.findById(dto.getContentId());
            if (descDto == null) {
                continue;
            }
            result.add(new SubResult<>(dto, descDto.getOverview()));
        }

        if (!result.isEmpty()) {
            return new ResponseEntity<>(new Result(HttpStatus.OK.value(), result), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(HttpStatus.NOT_FOUND.value(), "데이터를 찾을 수 없습니다."));
        }
    }

    @PostMapping(value = "/view")
    public ResponseEntity<?> tripDescription(@RequestParam(value = "contentId") String contentId) throws Exception {
        AttractionDescDto attractionDescDto = attractionInfoService.searchTripDescription(contentId);
        if (attractionDescDto != null) {
            return new ResponseEntity<AttractionDescDto>(attractionDescDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/sido")
    public ResponseEntity<?> tripSidoList() throws Exception {
        List<SidoDto> sidoList = attractionInfoService.getSido();
        if (sidoList != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(HttpStatus.OK.value(), sidoList));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/gugun")
    public ResponseEntity<?> tripGugunList(@RequestParam("sido") int sidoCode) throws Exception {
        List<GugunDto> gugunList = attractionInfoService.getGugun(sidoCode);
        if (gugunList != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(HttpStatus.OK.value(), gugunList));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/contentType")
    public ResponseEntity<?> tripContentType() throws Exception {
        List<ContentTypeDto> types = attractionInfoService.getContentTypes();
        if (types != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(HttpStatus.OK.value(), types));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Data
    @Builder
    static class Result<T> {
        int status;
        T data;
    }

    @Data
    @AllArgsConstructor
    static class SubResult<T> {
        AttractionInfoDto info;
        String desc;
    }
}
