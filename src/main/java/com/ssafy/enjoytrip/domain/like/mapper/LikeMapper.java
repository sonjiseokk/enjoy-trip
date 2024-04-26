package com.ssafy.enjoytrip.domain.like.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.domain.like.model.LikeDto;

@Mapper
public interface LikeMapper {
	public void registLike(LikeDto likeDto) throws Exception;
	public List<LikeDto> listLike(String userId) throws Exception;
	public void deleteLike (String userId) throws Exception;
}
