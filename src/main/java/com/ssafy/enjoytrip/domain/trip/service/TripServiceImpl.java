package com.ssafy.enjoytrip.domain.trip.service;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.domain.trip.controller.request.TripSearchCondition;
import com.ssafy.enjoytrip.domain.trip.mapper.TripMapper;
import com.ssafy.enjoytrip.domain.trip.model.GugunDto;
import com.ssafy.enjoytrip.domain.trip.model.SidoDto;
import com.ssafy.enjoytrip.domain.trip.model.TripDescriptionDto;
import com.ssafy.enjoytrip.domain.trip.model.TripDto;

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
	public JSONObject getSido() throws Exception {
		List<SidoDto> sidoList = tripMapper.getSido();
		
		JSONObject json = new JSONObject();
		
		JSONArray jsonArray = new JSONArray();
		for(SidoDto sido : sidoList) {
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("sido_code", sido.getSidoCode());
			jsonObj.put("sido_name", sido.getSidoName());
			
			jsonArray.put(jsonObj);
		}
		
		json.put("body", jsonArray);
		
		return json;
	}

	@Override
	public JSONObject getGugun(int sidoCode) throws Exception {
		
		List<GugunDto> gugunList = tripMapper.getGugun(sidoCode);
		
		JSONObject json = new JSONObject();
		
		JSONArray jsonArray = new JSONArray();
		for(GugunDto gugun : gugunList) {
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("gugun_code", gugun.getGugunCode());
			jsonObj.put("gugun_name", gugun.getGugunName());
			
			jsonArray.put(jsonObj);
		}
		
		json.put("body", jsonArray);
		
		return json;
	}

	@Override
	public TripDto getTrip(int contentId) throws Exception {
		return tripMapper.getTrip(contentId);
	}
}