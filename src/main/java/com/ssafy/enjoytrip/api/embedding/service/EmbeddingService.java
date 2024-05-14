package com.ssafy.enjoytrip.api.embedding.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.api.embedding.mapper.EmbeddingMapper;
import com.ssafy.enjoytrip.api.embedding.model.EmbeddingDto;
import com.ssafy.enjoytrip.global.exception.DuplicateNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmbeddingService {
    private final EmbeddingMapper embeddingMapper;
    private final InternalSearchService internalSearchService;
    private final EmbeddingClient embeddingClient;
    public int join(String name) throws Exception{
        try {
            if (embeddingMapper.findByName(name) != null) throw new DuplicateNameException("이미 존재하는 임베딩 데이터입니다.");

            EmbeddingResponse embeddingResponse = embeddingClient.embedForResponse(List.of(name));

            double[] vector = getVector(embeddingResponse);

            EmbeddingDto embeddingDto = EmbeddingDto.builder()
                    .embeddingName(name)
                    .vector(vector)
                    .build();
            embeddingMapper.save(embeddingDto);
            return embeddingDto.getContentId();

        } catch (DuplicateNameException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("임베딩 저장에 실패했습니다.");
        }
    }



    public List<EmbeddingDto> findAll() throws Exception {
        try {
            return embeddingMapper.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("임베딩 서비스가 실패했습니다.");
        }
    }

    public EmbeddingDto findByName(String name) throws Exception {
        try {
            return embeddingMapper.findByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("임베딩 서비스가 실패했습니다.");
        }
    }

    private static double[] getVector(final EmbeddingResponse embeddingResponse) {
        List<Double> doubles = embeddingResponse.getResult().getOutput();
        return doubles.stream()
                .mapToDouble(Double::doubleValue)
                .toArray();
    }
}
