package com.ssafy.enjoytrip.domain.trip.service;

import java.util.List;

import com.ssafy.enjoytrip.domain.trip.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.domain.trip.controller.request.TripSearchCondition;
import com.ssafy.enjoytrip.domain.trip.mapper.AttractionInfoMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttractionInfoService {
	
	private final AttractionInfoMapper attractionInfoMapper;
	
	// 기본 info list
	public List<AttractionInfoDto> searchTrip(TripSearchCondition con) throws Exception {
		List<AttractionInfoDto> lists = attractionInfoMapper.searchTrip(con);
		return lists;
	}

	// 기본 info + overView
	public AttractionDescDto searchTripDescription(String contentId) throws Exception {
		return attractionInfoMapper.searchTripDescription(contentId);
	}

	public List<SidoDto> getSido() throws Exception {
		return attractionInfoMapper.getSido();
	}

	public List<GugunDto> getGugun(int sidoCode) throws Exception {
		return attractionInfoMapper.getGugun(sidoCode);
	}

	public AttractionInfoDto getTrip(int contentId) throws Exception {
		return attractionInfoMapper.getTrip(contentId);
	}

	public List<ContentTypeDto> getContentTypes() throws Exception {
		return attractionInfoMapper.getContentTypes();
	}
	public List<AttractionInfoDto> getAllDto() throws Exception {
		return attractionInfoMapper.findAll();
	}

	@Transactional
	public void insertTrip(AttractionInfoDto dto) throws Exception{
		attractionInfoMapper.save(dto);
	}
}