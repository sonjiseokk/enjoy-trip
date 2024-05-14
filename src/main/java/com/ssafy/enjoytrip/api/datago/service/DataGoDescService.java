package com.ssafy.enjoytrip.api.datago.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.api.datago.model.TripItemDto;
import com.ssafy.enjoytrip.api.datago.model.response.DescApiResponse;
import com.ssafy.enjoytrip.domain.trip.mapper.AttractionInfoMapper;
import com.ssafy.enjoytrip.domain.trip.model.AttractionDescDto;
import com.ssafy.enjoytrip.domain.trip.model.AttractionInfoDto;
import com.ssafy.enjoytrip.domain.trip.service.AttractionDescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.ssafy.enjoytrip.api.datago.model.response.DescApiResponse.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataGoDescService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final AttractionInfoMapper attractionInfoMapper;
    private final AttractionDescriptionService attractionDescriptionService;
    public void getDescList(int contentId) throws Exception {
        // 하드코딩된 URL
//        String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=wcRHiVOvmjix4S7zSPQtgL%2F21iI0rhaUP9h1tpm0o2Daihy8Loue%2F0zyveUmedwhOcsE9%2Bcui3hkEudEAQiyuA%3D%3D&numOfRows=1000&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A";
        String url = "https://apis.data.go.kr/B551011/KorService1/detailCommon1" +
//                "?serviceKey=wcRHiVOvmjix4S7zSPQtgL%2F21iI0rhaUP9h1tpm0o2Daihy8Loue%2F0zyveUmedwhOcsE9%2Bcui3hkEudEAQiyuA%3D%3D" +
                "?serviceKey=Q0MR3QSTJqaZFrXpIOb7lBY7Z8LJx8cBz3IxRFBdDrEwbnUdn11uJ1ntwaBj9ZIypnEnvlWR6eGP3w/TEexEfA==" +
                "&MobileOS=ETC" +
                "&MobileApp=AppTest" +
                "&_type=json" +
                "&contentId=" + contentId +
                "&overviewYN=Y";

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
            DescApiResponse descApiResponse = objectMapper.readValue(responseBody, DescApiResponse.class);
            List<DescDto> items = descApiResponse.getResponse().getBody().getItems().getItem();

            for (DescDto item : items) {
                AttractionDescDto attractionDescDto = toDescDto(item);

                System.out.println(attractionDescDto.getContentId());

                try {
                    attractionDescriptionService.saveAttractionDesc(attractionDescDto);
                } catch (Exception e) {
                    continue;
                }
            }
        } else {
            throw new Exception("API 요청에 실패했습니다.");
        }
    }

    private AttractionDescDto toDescDto(DescDto item) {
        return new AttractionDescDto(
                item.getContentId(),
                item.getOverview()
        );
    }

}
