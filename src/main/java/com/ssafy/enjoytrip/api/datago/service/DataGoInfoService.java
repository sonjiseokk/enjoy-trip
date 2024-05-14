package com.ssafy.enjoytrip.api.datago.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.api.datago.model.TripItemDto;
import com.ssafy.enjoytrip.api.datago.model.request.ApiResponse;
import com.ssafy.enjoytrip.api.embedding.service.EmbeddingService;
import com.ssafy.enjoytrip.domain.trip.mapper.TripMapper;
import com.ssafy.enjoytrip.domain.trip.model.TripDto;
import com.ssafy.enjoytrip.domain.trip.service.AttractionDescriptionService;
import com.ssafy.enjoytrip.domain.trip.service.TripService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataGoInfoService {

    @Value("${api.service.key}")
    private String serviceKey;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final JdbcTemplate jdbcTemplate;
    private final TripMapper tripMapper;
    private final EmbeddingService embeddingService;
    private final AttractionDescriptionService attractionDescriptionService;


    public void getAreaBasedList(int pageNo, int numOfRows) throws Exception {
        // 하드코딩된 URL
        String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=wcRHiVOvmjix4S7zSPQtgL%2F21iI0rhaUP9h1tpm0o2Daihy8Loue%2F0zyveUmedwhOcsE9%2Bcui3hkEudEAQiyuA%3D%3D&numOfRows=1000&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A";

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();
            ApiResponse apiResponse = objectMapper.readValue(responseBody, ApiResponse.class);
            List<TripItemDto> items = apiResponse.getResponse().getBody().getItems().getItem();

            for (TripItemDto item : items) {
                TripDto tripDto = toTripDto(item);
                String title = tripDto.getTitle();
                System.out.println(title);

                tripMapper.save(tripDto);
            }
        } else {
            throw new Exception("API 요청에 실패했습니다.");
        }
    }

    private TripDto toTripDto(TripItemDto item) {
        return new TripDto(
                item.getContentId(),
                item.getTitle(),
                item.getAddress(),
                item.getThumbnailImage(),
                0, // readcount - 적절한 값을 설정하세요
                item.getLatitude(),
                item.getLongitude(),
                item.getMLevel(),
                item.getGugunCode(),
                item.getSidoCode(),
                item.getContentTypeId()
        );
    }
}
