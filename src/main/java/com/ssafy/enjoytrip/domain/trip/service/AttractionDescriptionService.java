package com.ssafy.enjoytrip.domain.trip.service;

import com.ssafy.enjoytrip.domain.trip.mapper.AttractionDescriptionMapper;
import com.ssafy.enjoytrip.domain.trip.model.AttractionDescDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class AttractionDescriptionService {
    private final AttractionDescriptionMapper mapper;

    public int saveAttractionDesc(AttractionDescDto dto) throws Exception {
        try {
            return mapper.save(dto);
        } catch (SQLException e) {
            throw new Exception("관광지 저장에 실패했습니다.");
        }

    }
}
