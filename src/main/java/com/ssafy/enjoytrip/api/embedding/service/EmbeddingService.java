package com.ssafy.enjoytrip.api.embedding.service;

import com.ssafy.enjoytrip.api.embedding.mapper.EmbeddingMapper;
import com.ssafy.enjoytrip.api.embedding.model.EmbeddingDto;
import com.ssafy.enjoytrip.api.embedding.model.SimilarDto;
import com.ssafy.enjoytrip.domain.trip.model.AttractionInfoDto;
import com.ssafy.enjoytrip.domain.trip.service.AttractionDescriptionService;
import com.ssafy.enjoytrip.domain.trip.service.AttractionInfoService;
import com.ssafy.enjoytrip.global.exception.DuplicateNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmbeddingService {
    private final EmbeddingMapper embeddingMapper;
    private final InternalSearchService internalSearchService;
    private final EmbeddingClient embeddingClient;
    private final AttractionInfoService attractionInfoService;
    private final AttractionDescriptionService attractionDescriptionService;

    public int join(String title) throws Exception {
        try {
            if (embeddingMapper.findByTitle(title) != null) throw new DuplicateNameException("이미 존재하는 임베딩 데이터입니다.");

            AttractionInfoDto info = attractionInfoService.findAttractionInfo(title);
            String sidoName = attractionInfoService.findNameBySidoCode(info.getSidoCode());
            String gugunName = attractionInfoService.findNameByGugunCode(info.getGugunCode(), info.getSidoCode());
            String category = attractionInfoService.findNameByContentTypeId(info.getContentTypeId());

            EmbeddingResponse embeddingResponse = embeddingClient.embedForResponse(List.of(title));

            double[] vector = getVector(embeddingResponse);
            EmbeddingDto embeddingDto = EmbeddingDto.builder()
                    .contentId(info.getContentId())
                    .embeddingName(String.format("name : %s | location : %s | latitude : %f | longitude : %f | sido : %s | gugun : %s | category : %s",
                            info.getTitle(), info.getAddress(), info.getLatitude(), info.getLongitude(), sidoName, gugunName, category)
                    )
                    .title(info.getTitle())
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

    public List<SimilarDto> getMostFive(String title) throws Exception {
        try {
            EmbeddingDto findByTitle = embeddingMapper.findByTitle(title);
            List<EmbeddingDto> all = embeddingMapper.findAll();

            return internalSearchService.findMostSimilarEmbeddings(findByTitle, all, 5);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("임베딩 서비스 로직에 실패했습니다.");
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
            return embeddingMapper.findByTitle(name);
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

    public List<SimilarDto> myMostFive(final List<AttractionInfoDto> myLikes) throws Exception {
        try {
            HashSet<SimilarDto> result = new HashSet<>();
            Set<String> myLikeTitles = myLikes.stream()
                    .map(AttractionInfoDto::getTitle)
                    .collect(Collectors.toSet());

            for (AttractionInfoDto myLike : myLikes) {
                result.addAll(getMostFive(myLike.getTitle()));
            }

            // myLikeTitles에 포함되지 않은 SimilarDto만 필터링
            return result.stream()
                    .filter(dto -> !myLikeTitles.contains(dto.getTitle()))
                    .sorted((d1, d2) -> Double.compare(d2.getSimilarity(), d1.getSimilarity()))
                    .limit(5)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("most 5를 불러오는데 실패했습니다.");
        }
    }
}
