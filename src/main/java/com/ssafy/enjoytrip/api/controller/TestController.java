package com.ssafy.enjoytrip.api.controller;

import com.ssafy.enjoytrip.api.datago.service.DataGoDescService;
import com.ssafy.enjoytrip.api.datago.service.DataGoInfoService;
import com.ssafy.enjoytrip.api.embedding.model.EmbeddingDto;
import com.ssafy.enjoytrip.api.embedding.service.EmbeddingService;
import com.ssafy.enjoytrip.api.moderation.model.response.ModerationResponse;
import com.ssafy.enjoytrip.api.moderation.service.ModerationService;
import com.ssafy.enjoytrip.domain.trip.model.AttractionInfoDto;
import com.ssafy.enjoytrip.domain.trip.service.AttractionInfoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {
    private final EmbeddingService embeddingService;
    private final ModerationService moderationService;
    private final DataGoInfoService dataGoInfoService;
    private final DataGoDescService dataGoDescService;
    private final AttractionInfoService attractionInfoService;
    @GetMapping("/ai/embedding/add")
    public Map embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) throws Exception {
        log.info("내가 받은 메시지는 = {}", message);

        embeddingService.join(message);

        EmbeddingDto dto = embeddingService.findByName(message);
        return Map.of("result", dto);
    }

    @GetMapping("/ai/embedding/")
    public Map embed() throws Exception {
        List<EmbeddingDto> all = embeddingService.findAll();
        return Map.of("result", all);
    }

    @GetMapping("/moderation/test")
    public Map moderation(@RequestParam("message") String message) throws Exception {
        List<ModerationResponse> responses = moderationService.calculateModeration(message);
        return Map.of("result", responses);
    }

    @GetMapping("/add/data")
    public void addData() throws Exception {

        dataGoInfoService.getAreaBasedList(1, 1000);
    }
    @GetMapping("/add/data2")
    public void addData2() throws Exception {

        List<AttractionInfoDto> allDto = attractionInfoService.getAllDto();
        for (int i = 1300; i < allDto.size(); i++) {
            AttractionInfoDto attractionInfoDto = allDto.get(i);
            dataGoDescService.getDescList(attractionInfoDto.getContentId());
        }
    }
}
