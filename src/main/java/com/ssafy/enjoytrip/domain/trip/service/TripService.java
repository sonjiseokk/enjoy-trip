package com.ssafy.enjoytrip.domain.trip.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.enjoytrip.domain.trip.model.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.domain.trip.controller.request.TripSearchCondition;
import com.ssafy.enjoytrip.domain.trip.mapper.TripMapper;

@Service
public class TripService {
	
	private final TripMapper tripMapper;
	
	@Autowired
	private TripService(TripMapper tripMapper) {
		this.tripMapper = tripMapper;
	}
	
	// 기본 info list
	public List<TripDto> searchTrip(TripSearchCondition con) throws Exception {
		List<TripDto> lists = tripMapper.searchTrip(con);
		return lists;
	}

	// 기본 info + overView
	public TripDescriptionDto searchTripDescription(String contentId) throws Exception {
		return tripMapper.searchTripDescription(contentId);
	}

	public List<SidoDto> getSido() throws Exception {
		return tripMapper.getSido();
	}

	public List<GugunDto> getGugun(int sidoCode) throws Exception {
		return tripMapper.getGugun(sidoCode);
	}

	public TripDto getTrip(int contentId) throws Exception {
		return tripMapper.getTrip(contentId);
	}

	public List<ContentTypeDto> getContentTypes() throws Exception {
		return tripMapper.getContentTypes();
	}

	public void insertTrip(TripDto dto) throws Exception{
		tripMapper.save(dto);
	}
}