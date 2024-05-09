package com.ssafy.enjoytrip.domain.trip.service;

import java.util.List;

import com.ssafy.enjoytrip.domain.trip.model.*;
import org.json.JSONObject;

import com.ssafy.enjoytrip.domain.trip.controller.request.TripSearchCondition;

public interface TripService {
	List<TripDto> searchTrip(TripSearchCondition con) throws Exception;
	TripDescriptionDto searchTripDescription(String contentId) throws Exception;
	List<SidoDto> getSido() throws Exception;
	List<GugunDto> getGugun(int sidoCode) throws Exception;
	TripDto getTrip(int contentId) throws Exception;

	List<ContentTypeDto> getContentTypes() throws Exception;
}
