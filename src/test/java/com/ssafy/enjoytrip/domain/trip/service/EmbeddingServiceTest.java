package com.ssafy.enjoytrip.domain.trip.service;

import com.ssafy.enjoytrip.domain.trip.mapper.EmbeddingMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmbeddingServiceTest {
    @Autowired
    private EmbeddingService embeddingService;
    @Autowired
    private EmbeddingMapper embeddingMapper;
    @Test
    @DisplayName("테스트")
    void 테스트() throws Exception {
        //given
        double[] v1 = embeddingService.getVector("SSAFY");
        double[] v2 = embeddingService.getVector("Dog");

        //when
        double v = embeddingService.calculateDotProduct("SSAFY","Dog");
        System.out.println(v);
        //then

    }

}