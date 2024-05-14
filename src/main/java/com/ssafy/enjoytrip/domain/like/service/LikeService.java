package com.ssafy.enjoytrip.domain.like.service;

import java.util.List;

import com.ssafy.enjoytrip.domain.like.model.LikeDto;
import com.ssafy.enjoytrip.domain.trip.model.AttractionInfoDto;

public interface LikeService {
	void registLike(String userId, int contentId) throws Exception;
	
	List<LikeDto> listLike(String userId) throws Exception;
	
	void deleteLike(String userId) throws Exception;
	
	List<AttractionInfoDto> optimalPath(String userId, int contentId) throws Exception;
}
