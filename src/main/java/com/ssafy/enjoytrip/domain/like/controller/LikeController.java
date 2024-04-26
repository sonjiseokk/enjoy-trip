package com.ssafy.enjoytrip.domain.like.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.enjoytrip.domain.like.model.LikeDto;
import com.ssafy.enjoytrip.domain.like.service.LikeService;
import com.ssafy.enjoytrip.domain.trip.model.TripDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {
	private final LikeService likeService;
	
	@PostMapping(value = "/regist")
	public ResponseEntity<?> tripList(@RequestParam("userId") String userId, @RequestParam("contentId") String contentId) throws Exception {
		likeService.registLike(userId, contentId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
