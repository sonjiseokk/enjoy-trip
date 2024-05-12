package com.ssafy.enjoytrip.api.embedding.mapper;

import com.ssafy.enjoytrip.api.embedding.model.EmbeddingDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface EmbeddingMapper {
    List<EmbeddingDto> findAll() throws SQLException;
    EmbeddingDto findByName(String name) throws SQLException;
    void save(EmbeddingDto embeddingDto) throws SQLException;
}