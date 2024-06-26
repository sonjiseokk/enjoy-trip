package com.ssafy.enjoytrip.api.embedding.service;

import com.ssafy.enjoytrip.api.embedding.model.EmbeddingDto;
import com.ssafy.enjoytrip.api.embedding.model.SimilarDto;
import com.ssafy.enjoytrip.domain.trip.model.AttractionInfoDto;
import com.ssafy.enjoytrip.domain.trip.service.AttractionInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalSearchService {
    private static final double SIMILARITY_THRESHOLD = 0.8; // Just an example threshold to limit the results

    public List<SimilarDto> findMostSimilarEmbeddings(EmbeddingDto queryEmbedding, List<EmbeddingDto> dbEmbeddings, int topResults) {

        Collections.shuffle(dbEmbeddings);

        List<SimilarDto> mostSimilarIndices = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            EmbeddingDto dbEmbedding = dbEmbeddings.get(i);
            double similarity = cosineSimilarity(queryEmbedding.getVector(), dbEmbedding.getVector());
            if (similarity >= SIMILARITY_THRESHOLD) {
                mostSimilarIndices.add(SimilarDto.builder()
                        .title(dbEmbedding.getTitle())
                        .sourceTitle(queryEmbedding.getTitle())
                        .similarity(similarity)
                        .build());
            }
        }

        List<SimilarDto> result = mostSimilarIndices.subList(0, Math.min(topResults, mostSimilarIndices.size()));
        Collections.shuffle(result);

        return result;
    }

    private double cosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

}
