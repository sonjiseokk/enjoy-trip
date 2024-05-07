package com.ssafy.enjoytrip.domain.trip.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.domain.trip.mapper.EmbeddingMapper;
import com.ssafy.enjoytrip.domain.trip.model.EmbeddingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class EmbeddingService {
    private final EmbeddingMapper embeddingMapper;
    private final ObjectMapper objectMapper;

    public double[] getVector(String name) throws SQLException {
        EmbeddingDto embeddingDto = embeddingMapper.findByName(name);
        if (embeddingDto != null && embeddingDto.getVector() != null) {
            try {
                return objectMapper.readValue(embeddingDto.getVector(), double[].class);
            } catch (IOException e) {
                throw new RuntimeException("Failed to parse vector JSON", e);
            }
        }
        return new double[0]; // 또는 예외 처리
    }

    public double calculateDotProduct(String name1, String name2) throws SQLException {
        double[] vector1 = getVector(name1);
        double[] vector2 = getVector(name2);
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Vectors must be of the same length.");
        }
        double result = 0.0;
        for (int i = 0; i < vector1.length; i++) {
            result += vector1[i] * vector2[i];
        }
        return result;
    }

}
