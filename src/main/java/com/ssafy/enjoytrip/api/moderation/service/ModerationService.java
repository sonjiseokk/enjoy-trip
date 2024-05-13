package com.ssafy.enjoytrip.api.moderation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.api.moderation.model.request.ModerationAPIRequest;
import com.ssafy.enjoytrip.api.moderation.model.response.ModerationAPIResponse;
import com.ssafy.enjoytrip.api.moderation.model.response.ModerationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModerationService {

    @Value("${moderations.api.key}")
    private String key;
    private final RestTemplate restTemplate;

    public List<ModerationResponse> calculateModeration(String message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info(key);
        String url = "https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key=" + key;

        ModerationAPIRequest request = new ModerationAPIRequest(message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(request), headers);

        ResponseEntity<ModerationAPIResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                ModerationAPIResponse.class
        );

        ModerationAPIResponse response = responseEntity.getBody();

        List<ModerationResponse> responses = new ArrayList<>();
        if (response != null && response.getAttributeScores() != null) {
            for (Map.Entry<String, ModerationAPIResponse.AttributeScores> entry : response.getAttributeScores().entrySet()) {
                String attributeName = entry.getKey();
                double summaryScoreValue = entry.getValue().getSummaryScore().getValue();

                ModerationResponse moderationResponse = ModerationResponse.builder()
                        .attributeName(attributeName)
                        .score(summaryScoreValue)
                        .build();
                responses.add(moderationResponse);
            }
        } else {
            throw new Exception("Moderation API 요청에 실패했습니다.");
        }
        return responses;
    }
}
