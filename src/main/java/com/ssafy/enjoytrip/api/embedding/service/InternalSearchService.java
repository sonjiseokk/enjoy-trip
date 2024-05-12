package com.ssafy.enjoytrip.api.embedding.service;

import com.ssafy.enjoytrip.api.embedding.model.SimilarDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class InternalSearchService {

    private static final double SIMILARITY_THRESHOLD = 0.6; // Just an example threshold to limit the results

    public List<SimilarDto> findMostSimilarEmbeddings(List<Double> queryEmbedding, List<double[]> dbEmbeddings, int topResults) {
        List<Double> similarities = new ArrayList<>();
        for (double[] dbEmbedding : dbEmbeddings) {
            double similarity = cosineSimilarity(queryEmbedding, dbEmbedding);
            similarities.add(similarity);
        }

        List<SimilarDto> mostSimilarIndices = new ArrayList<>();
        for (int i = 0; i < dbEmbeddings.size(); i++) {

            // Only consider indices with similarity above the threshold
            if (similarities.get(i) >= SIMILARITY_THRESHOLD)
                mostSimilarIndices.add(new SimilarDto(i,similarities.get(i)));
        }

        mostSimilarIndices.sort(Comparator.comparingDouble(SimilarDto::getSimilarity).reversed());

        return mostSimilarIndices.subList(0, Math.min(topResults, mostSimilarIndices.size()));
    }

    private double cosineSimilarity(List<Double> vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.size(); i++) {
            dotProduct += vectorA.get(i) * vectorB[i];
            normA += Math.pow(vectorA.get(i), 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

}
