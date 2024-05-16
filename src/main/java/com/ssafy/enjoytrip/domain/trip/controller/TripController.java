package com.ssafy.enjoytrip.domain.trip.controller;

import java.util.List;

import com.ssafy.enjoytrip.domain.trip.model.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ssafy.enjoytrip.domain.trip.controller.request.TripSearchCondition;
import com.ssafy.enjoytrip.domain.trip.service.AttractionInfoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/trip")
@CrossOrigin(origins = "*")
public class TripController {
    private final AttractionInfoService attractionInfoService;

    @PostMapping(value = "/search")
    public ResponseEntity<?> tripList(@RequestBody TripSearchCondition con) throws Exception {
        List<AttractionInfoDto> list = attractionInfoService.searchTrip(con);
        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<List<AttractionInfoDto>>(list, HttpStatus.OK);
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
}
