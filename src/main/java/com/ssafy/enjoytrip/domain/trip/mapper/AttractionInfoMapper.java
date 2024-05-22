package com.ssafy.enjoytrip.domain.trip.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.domain.trip.model.*;
import com.ssafy.enjoytrip.global.RequestList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttractionInfoMapper {
	List<AttractionInfoDto> searchTrip(RequestList<Object> request) throws Exception;
	List<AttractionInfoDto> searchAll(String keyword) throws Exception;
	AttractionDescDto searchTripDescription(int contentId) throws Exception;
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
