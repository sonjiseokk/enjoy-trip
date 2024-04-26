package com.ssafy.enjoytrip.domain.like.service;

import java.util.List;

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
	public void registLike(String userId, int contentId) throws Exception {
		LikeDto likeDto = new LikeDto(userId, contentId);
		likeMapper.registLike(likeDto);
	}

	@Override
	public List<LikeDto> listLike(String userId) throws Exception {
		return likeMapper.listLike(userId);
	}

	@Override
	public void deleteLike(String userId) throws Exception {
		likeMapper.deleteLike(userId);
	}
}
