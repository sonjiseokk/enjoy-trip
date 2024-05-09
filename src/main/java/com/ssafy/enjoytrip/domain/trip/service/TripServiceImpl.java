package com.ssafy.enjoytrip.domain.trip.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.enjoytrip.domain.trip.model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.domain.trip.controller.request.TripSearchCondition;
import com.ssafy.enjoytrip.domain.trip.mapper.TripMapper;

@Service
public class TripServiceImpl implements TripService {
	
	private final TripMapper tripMapper;
	
	@Autowired
	private TripServiceImpl(TripMapper tripMapper) {	
		this.tripMapper = tripMapper;
	}
	
	// 기본 info list
	@Override
	public List<TripDto> searchTrip(TripSearchCondition con) throws Exception {
		List<TripDto> lists = tripMapper.searchTrip(con);
		return lists;
	}

	// 기본 info + overView
	@Override
	public TripDescriptionDto searchTripDescription(String contentId) throws Exception {
		return tripMapper.searchTripDescription(contentId);
	}

	@Override
	public List<SidoDto> getSido() throws Exception {
		return tripMapper.getSido();
	}

	@Override
	public List<GugunDto> getGugun(int sidoCode) throws Exception {
		return tripMapper.getGugun(sidoCode);
	}

	@Override
	public TripDto getTrip(int contentId) throws Exception {
		return tripMapper.getTrip(contentId);
	}

	@Override
	public List<ContentTypeDto> getContentTypes() throws Exception {
		return tripMapper.getContentTypes();
	}
}