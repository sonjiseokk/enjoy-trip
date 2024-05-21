package com.ssafy.enjoytrip.api.embedding.service;

import com.ssafy.enjoytrip.api.embedding.mapper.EmbeddingMapper;
import com.ssafy.enjoytrip.api.embedding.model.EmbeddingDto;
import com.ssafy.enjoytrip.api.embedding.model.SimilarDto;
import com.ssafy.enjoytrip.domain.trip.model.AttractionInfoDto;
import com.ssafy.enjoytrip.domain.trip.service.AttractionDescriptionService;
import com.ssafy.enjoytrip.domain.trip.service.AttractionInfoService;
import com.ssafy.enjoytrip.global.exception.DuplicateNameException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
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
                    .embeddingName(String.format("name : %s | location : %s | sido : %s | gugun : %s | category : %s",
                            info.getTitle(), info.getAddress(), sidoName, gugunName, category)
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

    public List<SimilarDto> getMostTen(String title) throws Exception {
        try {
            EmbeddingDto findByTitle = embeddingMapper.findByTitle(title);
            if (findByTitle == null) {
                throw new Exception("임베딩이 지원되지 않는 관심목록이 추가되어있습니다.");
            }
            List<EmbeddingDto> all = embeddingMapper.findAll();

            return internalSearchService.findMostSimilarEmbeddings(findByTitle, all, 10);
        } catch (Exception e) {
            log.error(e.getMessage());
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

    public List<SimilarDto> mostTen(final List<AttractionInfoDto> myLikes, AttractionInfoDto dto) throws Exception {
        List<SimilarDto> containMeMostTen = getMostTen(dto.getTitle());
        return containMeMostTen.stream()
                .filter(similarDto -> !similarDto.getTitle().equals(dto.getTitle()))
                .filter(similarDto -> myLikes.stream().noneMatch(like -> like.getTitle().equals(similarDto.getTitle())))
                .sorted((d1, d2) -> Double.compare(d2.getSimilarity(), d1.getSimilarity()))
                .limit(10)
                .toList();
    }

    public AttractionInfoDto pickRandomAttraction(final List<AttractionInfoDto> myLikes) throws Exception {
        if (myLikes == null || myLikes.isEmpty()) {
            throw new Exception("The list of attractions is empty or null");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(myLikes.size());
        return myLikes.get(randomIndex);
    }

    /**
     * 내 좋아요 목록을 토대로 유사한 관광지 10개를 뽑아내는 메소드
     *
     * @param myLikes
     * @return
     * @throws Exception
     */
    @Deprecated
    public List<SimilarDto> myMostTen(final List<AttractionInfoDto> myLikes) throws Exception {
        try {
            HashSet<SimilarDto> result = new HashSet<>();
            Set<String> myLikeTitles = myLikes.stream()
                    .map(AttractionInfoDto::getTitle)
                    .collect(Collectors.toSet());

            for (AttractionInfoDto myLike : myLikes) {
                result.addAll(getMostTen(myLike.getTitle()));
            }

            // myLikeTitles에 포함되지 않은 SimilarDto만 필터링
            return result.stream()
                    .filter(dto -> !myLikeTitles.contains(dto.getTitle()))
                    .sorted((d1, d2) -> Double.compare(d2.getSimilarity(), d1.getSimilarity()))
                    .limit(10)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("most 5를 불러오는데 실패했습니다.");
        }
    }


    private static double[] getVector(final EmbeddingResponse embeddingResponse) {
        List<Double> doubles = embeddingResponse.getResult().getOutput();
        return doubles.stream()
                .mapToDouble(Double::doubleValue)
                .toArray();
    }
}
