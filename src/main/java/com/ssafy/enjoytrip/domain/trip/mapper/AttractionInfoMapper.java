package com.ssafy.enjoytrip.domain.trip.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.domain.trip.model.*;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.domain.trip.controller.request.TripSearchCondition;

@Mapper
public interface AttractionInfoMapper {
	List<AttractionInfoDto> searchTrip(TripSearchCondition con) throws Exception;
	AttractionDescDto searchTripDescription(String contentId) throws Exception;
	AttractionDescDto searchOverview(String contentId) throws Exception;
	List<SidoDto> getSido() throws Exception;
	List<GugunDto> getGugun(int sidoCode) throws Exception;
	AttractionInfoDto getTrip(int contentId) throws Exception;
	List<ContentTypeDto> getContentTypes() throws Exception;
    void save(AttractionInfoDto dto) throws SQLException;

	List<AttractionInfoDto> findAll() throws SQLException;

    AttractionInfoDto findByName(String name);

	AttractionInfoDto findByContentId(int contentId);

	String findNameBySidoCode(int sidoCode) throws SQLException;

	String findNameByGugunCode(int gugunCode,int sidoCode);
	String findNameByContentTypeId(int contentTypeId);
}
