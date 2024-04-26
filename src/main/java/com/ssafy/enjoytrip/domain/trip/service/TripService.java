package com.ssafy.enjoytrip.domain.trip.service;

import java.util.List;

import org.json.JSONObject;

import com.ssafy.enjoytrip.domain.trip.controller.request.TripSearchCondition;
import com.ssafy.enjoytrip.domain.trip.model.TripDescriptionDto;
import com.ssafy.enjoytrip.domain.trip.model.TripDto;

public interface TripService {
	public List<TripDto> searchTrip(TripSearchCondition con) throws Exception;
	
	public TripDescriptionDto searchTripDescription(String contentId) throws Exception;
	
	public JSONObject getSido() throws Exception;
	
	public JSONObject getGugun(int sidoCode) throws Exception;
	
	public JSONObject detailView(String contentId) throws Exception;

//	public JSONObject searchTripJSON(String contentTypeId) throws Exception;
	
	public JSONObject optimalDist(String contentId, List<TripDto> tripList) throws Exception;
}
