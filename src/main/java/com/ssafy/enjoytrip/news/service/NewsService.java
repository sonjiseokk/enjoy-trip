package com.ssafy.enjoytrip.news.service;
//네이버 검색 API 예제 - 블로그 검색
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.news.model.NewsDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsService {
	@Value("${X-Naver-Client-Id}")
	String clientId = "cPj7bWvpd8YbY8kGPrOW"; //애플리케이션 클라이언트 아이디
	@Value("${X-Naver-Client-Secret}")
	String clientSecret = "lboRZ16rSF"; //애플리케이션 클라이언트 시크릿
	
	public List<NewsDto> getNews(String text) {
	     try {
	         text = URLEncoder.encode("그린팩토리", "UTF-8");
	     } catch (UnsupportedEncodingException e) {
	         throw new RuntimeException("검색어 인코딩 실패",e);
	     }
	
	     String apiURL = "https://openapi.naver.com/v1/search/news?query=" + text;    // JSON 결과
	     //String apiURL = "https://openapi.naver.com/v1/search/news.xml?query="+ text; // XML 결과

	     Map<String, String> requestHeaders = new HashMap<>();
	     requestHeaders.put("X-Naver-Client-Id", clientId);
	     requestHeaders.put("X-Naver-Client-Secret", clientSecret);
	     String responseBody = get(apiURL,requestHeaders);
	
	     ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 문자열을 JsonNode로 변환
            JsonNode rootNode = objectMapper.readTree(responseBody);
            // "items" 노드를 추출
            JsonNode itemsNode = rootNode.path("items");
            // "items" 노드를 List<NewsDto>로 변환
            List<NewsDto> newsList = objectMapper.readValue(itemsNode.toString(), new TypeReference<List<NewsDto>>() {});

//            // 결과 출력
//            for (NewsDto news : newsList) {
//                System.out.println(news);
//            }
            return newsList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	 }
	
	
	 private static String get(String apiUrl, Map<String, String> requestHeaders){
	     HttpURLConnection con = connect(apiUrl);
	     try {
	         con.setRequestMethod("GET");
	         for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
	             con.setRequestProperty(header.getKey(), header.getValue());
	         }
	
	
	         int responseCode = con.getResponseCode();
	         if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
	             return readBody(con.getInputStream());
	         } else { // 오류 발생
	             return readBody(con.getErrorStream());
	         }
	     } catch (IOException e) {
	         throw new RuntimeException("API 요청과 응답 실패", e);
	     } finally {
	         con.disconnect();
	     }
	 }
	
	
	 private static HttpURLConnection connect(String apiUrl){
	     try {
	         URL url = new URL(apiUrl);
	         return (HttpURLConnection)url.openConnection();
	     } catch (MalformedURLException e) {
	         throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
	     } catch (IOException e) {
	         throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
	     }
	 }
	
	
	 private static String readBody(InputStream body){
	     InputStreamReader streamReader = new InputStreamReader(body);
	
	
	     try (BufferedReader lineReader = new BufferedReader(streamReader)) {
	         StringBuilder responseBody = new StringBuilder();
	
	
	         String line;
	         while ((line = lineReader.readLine()) != null) {
	             responseBody.append(line);
	         }
	
	
	         return responseBody.toString();
	     } catch (IOException e) {
	         throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
	     }
	 }
}