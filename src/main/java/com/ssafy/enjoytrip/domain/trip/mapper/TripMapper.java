package com.ssafy.enjoytrip.domain.trip.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.domain.trip.model.*;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.domain.trip.controller.request.TripSearchCondition;

@Mapper
public interface TripMapper {
	List<TripDto> searchTrip(TripSearchCondition con) throws Exception;
	TripDescriptionDto searchTripDescription(String contentId) throws Exception;
	TripDescriptionDto searchOverview(String contentId) throws Exception;
	List<SidoDto> getSido() throws Exception;
	List<GugunDto> getGugun(int sidoCode) throws Exception;
	TripDto getTrip(int contentId) throws Exception;
	List<ContentTypeDto> getContentTypes() throws Exception;
    void save(TripDto dto) throws SQLException;
}
