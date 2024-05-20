package com.ssafy.enjoytrip.news.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.global.Result;
import com.ssafy.enjoytrip.news.model.NewsDto;
import com.ssafy.enjoytrip.news.service.NewsService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
	private NewsService newsService;
	
	@GetMapping("/list/{keyword}")
    public ResponseEntity<?> getNewsList(@PathVariable("keyword") String keyword, HttpServletRequest request) throws Exception {     	
    	List<NewsDto> newsList = newsService.getNews(keyword);
    	if (!newsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true,HttpStatus.OK.value(), newsList));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(false,HttpStatus.NOT_FOUND.value(), "등록된 게시물이 없습니다."));
        }
    }
	
    @Data
    @Builder
    static class Result<T> {
        boolean success;
        int status;
        T data;
    }
}
