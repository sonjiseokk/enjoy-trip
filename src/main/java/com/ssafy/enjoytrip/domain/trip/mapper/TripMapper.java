package com.ssafy.enjoytrip.domain.trip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.domain.trip.controller.request.TripSearchCondition;
import com.ssafy.enjoytrip.domain.trip.model.GugunDto;
import com.ssafy.enjoytrip.domain.trip.model.SidoDto;
import com.ssafy.enjoytrip.domain.trip.model.TripDescriptionDto;
import com.ssafy.enjoytrip.domain.trip.model.TripDto;

@Mapper
public interface TripMapper {
	public List<TripDto> searchTrip(TripSearchCondition con) throws Exception;
	
	public TripDescriptionDto searchTripDescription(String contentId) throws Exception;
	
	public TripDescriptionDto searchOverview(String contentId) throws Exception;
	
	public List<SidoDto> getSido() throws Exception;
	
	public List<GugunDto> getGugun(int sidoCode) throws Exception;
	
}
