package com.ssafy.enjoytrip.domain.board.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.enjoytrip.domain.board.model.BoardDto;
import com.ssafy.enjoytrip.domain.board.mapper.BoardMapper;
import com.ssafy.enjoytrip.global.util.Kmp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardMapper boardMapper;

    @Override
    public void writeArticle(String subject, String content, String userId) throws Exception {
        BoardDto boardDto = BoardDto.builder()
                .subject(subject)
                .content(content)
                .createDate(LocalDateTime.now().toString())
                .userId(userId)  // 사용자 ID 설정
                .build();
        try {
            boardMapper.writeArticle(boardDto);
        } catch (Exception e) {
            throw new Exception("게시물 작성중에 오류가 발생했습니다.");
        }
    }

    @Override
    public List<BoardDto> listArticle(String keyword) throws Exception {
        try {
            if (keyword.isEmpty()) {
                return boardMapper.listArticle();
            }
            return search(keyword);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("게시물 리스트 조회중에 오류가 발생했습니다.");
        }
    }

    @Override
    public List<BoardDto> search(String keyword) throws Exception {
        String[] keywords = keyword.split(" ");
        return Kmp.multiKmp(boardMapper.listArticle(), keywords);
    }


}
