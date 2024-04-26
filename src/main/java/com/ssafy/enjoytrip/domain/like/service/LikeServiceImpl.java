package com.ssafy.enjoytrip.domain.like.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.domain.like.mapper.LikeMapper;
import com.ssafy.enjoytrip.domain.like.model.LikeDto;

@Service
public class LikeServiceImpl implements LikeService {
	private LikeMapper likeMapper;
	
	@Autowired
	private LikeServiceImpl(LikeMapper likeMapper) {	
		this.likeMapper = likeMapper;
	}
	
	@Override
	public void registLike(String userId, String contentId) throws Exception {
		LikeDto likeDto = new LikeDto(userId, contentId);
		likeMapper.registLike(likeDto);
	}
}
