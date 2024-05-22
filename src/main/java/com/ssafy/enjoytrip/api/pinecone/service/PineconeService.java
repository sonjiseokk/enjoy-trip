package com.ssafy.enjoytrip.api.pinecone.service;

import com.ssafy.enjoytrip.api.embedding.model.EmbeddingDto;
import com.ssafy.enjoytrip.api.embedding.service.EmbeddingService;
import com.ssafy.enjoytrip.api.pinecone.PineConeDto;
import com.ssafy.enjoytrip.domain.trip.model.AttractionInfoDto;
import com.ssafy.enjoytrip.domain.trip.service.AttractionInfoService;
import io.pinecone.clients.Index;
import io.pinecone.clients.Pinecone;
import io.pinecone.exceptions.PineconeException;
import io.pinecone.proto.DescribeIndexStatsResponse;
import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;


@Service
@RequiredArgsConstructor
@Slf4j
public class PineconeService {
    private final EmbeddingService embeddingService;
    private final AttractionInfoService attractionInfoService;
    @Value("${pinecone.api.key}")
    private String apiKey;

    @Value("${pinecone.index.name}")
    private String indexName;
    private Index index;

    public void init() {
        Pinecone pc = new Pinecone.Builder(apiKey).build();
        index = pc.getIndexConnection(indexName);
    }

    public void upsertVectors(EmbeddingDto dto) {
        init();
        List<Float> values = DoubleStream.of(dto.getVector())
                .mapToObj(d -> (float) d)
                .toList();

        log.info("하나 등록합니다 = {}", dto.getTitle());
        // 네임스페이스 설정: location 정보를 네임스페이스로 사용
        try {
            index.upsert(String.valueOf(dto.getContentId()), values, "ssafy");
        } catch (PineconeException e) {
            throw new RuntimeException("Failed to upsert vectors", e);
        }
    }

    public List<PineConeDto> compareVectors(int topK, int contentId) throws Exception {
        init();
        EmbeddingDto dto = embeddingService.findByContentId(contentId);
        List<Float> queryVector = DoubleStream.of(dto.getVector())
                .mapToObj(d -> (float) d)
                .toList();
        QueryResponseWithUnsignedIndices queryResponse = index.query(topK, queryVector, null, null, null, "ssafy", null, true, false);


        List<PineConeDto> result = queryResponse.getMatchesList().stream()
                .map(match -> new PineConeDto(Integer.parseInt(match.getId()), match.getScore()))
                .collect(Collectors.toList());

        return result;
    }

}

