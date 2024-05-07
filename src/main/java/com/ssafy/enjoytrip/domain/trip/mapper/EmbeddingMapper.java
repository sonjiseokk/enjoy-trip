package com.ssafy.enjoytrip.domain.trip.mapper;

import com.ssafy.enjoytrip.domain.trip.model.EmbeddingDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface EmbeddingMapper {
    EmbeddingDto findByName(String name) throws SQLException;
}
