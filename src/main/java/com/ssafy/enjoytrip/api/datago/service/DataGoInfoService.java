package com.ssafy.enjoytrip.api.datago.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.api.datago.model.TripItemDto;
import com.ssafy.enjoytrip.api.datago.model.response.InfoApiResponse;
import com.ssafy.enjoytrip.domain.trip.mapper.AttractionInfoMapper;
import com.ssafy.enjoytrip.domain.trip.model.AttractionInfoDto;
import com.ssafy.enjoytrip.domain.trip.service.AttractionDescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataGoInfoService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final AttractionInfoMapper attractionInfoMapper;
    private final AttractionDescriptionService attractionDescriptionService;


    public void getAreaBasedList(int pageNo, int numOfRows) throws Exception {
        // 하드코딩된 URL
        String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=Q0MR3QSTJqaZFrXpIOb7lBY7Z8LJx8cBz3IxRFBdDrEwbnUdn11uJ1ntwaBj9ZIypnEnvlWR6eGP3w/TEexEfA==&numOfRows=1000&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=Q";
//        String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=wcRHiVOvmjix4S7zSPQtgL%2F21iI0rhaUP9h1tpm0o2Daihy8Loue%2F0zyveUmedwhOcsE9%2Bcui3hkEudEAQiyuA%3D%3D&numOfRows=10000&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=Q";

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
            InfoApiResponse infoApiResponse = objectMapper.readValue(responseBody, InfoApiResponse.class);
            List<TripItemDto> items = infoApiResponse.getResponse().getBody().getItems().getItem();

            for (TripItemDto item : items) {
                AttractionInfoDto attractionInfoDto = toTripDto(item);
                String title = attractionInfoDto.getTitle();
                System.out.println(title);

                try {

                    attractionInfoMapper.save(attractionInfoDto);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        } else {
            throw new Exception("API 요청에 실패했습니다.");
        }
    }

    private AttractionInfoDto toTripDto(TripItemDto item) {
        return new AttractionInfoDto(
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
