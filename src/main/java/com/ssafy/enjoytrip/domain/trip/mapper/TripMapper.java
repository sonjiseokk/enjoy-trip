package com.ssafy.enjoytrip.domain.trip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.domain.trip.model.GugunDto;
import com.ssafy.enjoytrip.domain.trip.model.SidoDto;
import com.ssafy.enjoytrip.domain.trip.model.TripDto;

@Mapper
public interface TripMapper {
	public List<TripDto> searchTrip(String contentTypeId, int sidoCode, int gugunCode) throws Exception;
	
	public TripDto searchTripDetail(String contentId) throws Exception;
	
	public String[] searchOverview(String contentId) throws Exception;
	
	public List<SidoDto> getSido() throws Exception;
	
	public List<GugunDto> getGugun(int sidoCode) throws Exception;
	
}
