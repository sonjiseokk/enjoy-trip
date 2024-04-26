package com.ssafy.enjoytrip.domain.like.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.domain.like.model.LikeDto;

@Mapper
public interface LikeMapper {
	public void registLike(LikeDto likeDto) throws Exception;
}
