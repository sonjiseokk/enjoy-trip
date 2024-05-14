package com.ssafy.enjoytrip.domain.trip.mapper;

import com.ssafy.enjoytrip.domain.trip.model.AttractionDescDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface AttractionDescriptionMapper {
    int save(AttractionDescDto dto) throws SQLException;
}
