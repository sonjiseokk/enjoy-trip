package com.ssafy.enjoytrip.api.embedding.controller;

import com.ssafy.enjoytrip.api.embedding.model.EmbeddingDto;
import com.ssafy.enjoytrip.api.embedding.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {
    private final EmbeddingService embeddingService;

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
}
