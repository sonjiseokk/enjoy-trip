package com.ssafy.enjoytrip.domain.like.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.enjoytrip.domain.like.model.LikeDto;
import com.ssafy.enjoytrip.domain.like.service.LikeService;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import com.ssafy.enjoytrip.domain.trip.model.AttractionInfoDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {
	private final LikeService likeService;
	
	@PostMapping(value = "/regist")
	public ResponseEntity<?> registLike(@RequestParam("userId") String userId, @RequestParam("contentId") int contentId) throws Exception {
		likeService.registLike(userId, contentId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/list")
	public ResponseEntity<?> listLike(@RequestParam("userId") String userId) throws Exception {
		List<LikeDto> likeList = likeService.listLike(userId);
		if(likeList != null && !likeList.isEmpty()) {
			return new ResponseEntity<>(likeList, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@PostMapping(value = "/delete")
	public ResponseEntity<?> registLike(@RequestParam("userId") String userId) throws Exception {
		likeService.deleteLike(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/optimalpath")
	public ResponseEntity<?> optimalPath(@RequestParam("contentId") int contentId, HttpSession session) throws Exception {
		if((MemberDto) session.getAttribute("userinfo") == null) System.out.println("dhkdl");
		
		String userId = ((MemberDto) session.getAttribute("userinfo")).getUserId();
		List<AttractionInfoDto> path = likeService.optimalPath(userId, contentId);
		if(path != null && !path.isEmpty()) {
			return new ResponseEntity<>(path, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
	}
	
}
