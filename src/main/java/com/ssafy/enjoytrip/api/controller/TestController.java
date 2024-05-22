package com.ssafy.enjoytrip.api.controller;

import com.ssafy.enjoytrip.api.datago.service.DataGoDescService;
import com.ssafy.enjoytrip.api.datago.service.DataGoInfoService;
import com.ssafy.enjoytrip.api.embedding.model.EmbeddingDto;
import com.ssafy.enjoytrip.api.embedding.model.SimilarDto;
import com.ssafy.enjoytrip.api.embedding.service.EmbeddingService;
import com.ssafy.enjoytrip.api.moderation.model.response.ModerationResponse;
import com.ssafy.enjoytrip.api.moderation.service.ModerationService;
import com.ssafy.enjoytrip.api.pinecone.service.PineconeService;
import com.ssafy.enjoytrip.domain.trip.model.AttractionInfoDto;
import com.ssafy.enjoytrip.domain.trip.service.AttractionDescriptionService;
import com.ssafy.enjoytrip.domain.trip.service.AttractionInfoService;
import com.ssafy.enjoytrip.domain.member.service.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/test")
@CrossOrigin(value = "*")
public class TestController {
    private final EmbeddingService embeddingService;
    private final ModerationService moderationService;
    private final DataGoInfoService dataGoInfoService;
    private final DataGoDescService dataGoDescService;
    private final AttractionInfoService attractionInfoService;
    private final AttractionDescriptionService attractionDescriptionService;
    private final VerificationService verificationService;
    private final PineconeService pineconeService;

    @GetMapping("/ai/embedding/add")
    public Map embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) throws Exception {
        log.info("내가 받은 메시지는 = {}", message);

        embeddingService.join(message);

        EmbeddingDto dto = embeddingService.findByName(message);
        return Map.of("result", dto);
    }

    @GetMapping("/ai/embedding/addAll")
    public Map embed() throws Exception {
        List<AttractionInfoDto> dbInfos = attractionInfoService.getAllDto();

        for (int i = 0; i < dbInfos.size(); i++) {
            System.out.println("저장할 데이터의 제목은 : " + dbInfos.get(i).getTitle());

            try {
                embeddingService.join(dbInfos.get(i).getTitle());
            } catch (Exception e) {
                continue;
            }
        }
        List<EmbeddingDto> all = embeddingService.findAll();
        return Map.of("result", all.size());
    }

    @GetMapping("/ai/embedding/most5")
    public ResponseEntity<?> most(@RequestParam String name) throws Exception {
        List<SimilarDto> similarityDto = embeddingService.getMostTen(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(similarityDto);
    }

    @GetMapping("/moderation/test")
    public Map moderation(@RequestParam("message") String message) throws Exception {
        List<ModerationResponse> responses = moderationService.calculateModeration(message);
        return Map.of("result", responses);
    }

    @GetMapping("/add/data")
    public void addData(@RequestParam("pageNo") int pageNo) throws Exception {

        dataGoInfoService.getAreaBasedList(pageNo, 1000);
    }

    @GetMapping("/add/data2")
    public void addData2(@RequestParam("pageNo") int pageNo) throws Exception {

        List<AttractionInfoDto> allDto = attractionInfoService.getAllDto();
        for (int i = pageNo; i < allDto.size(); i++) {
            AttractionInfoDto attractionInfoDto = allDto.get(i);
            dataGoDescService.getDescList(attractionInfoDto.getContentId());
        }
    }

    @GetMapping("/pinecone")
    public String pinecone() throws Exception {
        List<EmbeddingDto> all = embeddingService.findAll();

        for (EmbeddingDto embeddingDto : all) {
            try {
                pineconeService.upsertVectors(embeddingDto);
            } catch (Exception e) {
                log.info("에러 발생!!!!!!!!!!!!!!!!!!!!!!");
                continue;
            }
        }
        return "성공했다";
    }
}
