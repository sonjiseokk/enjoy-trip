package com.ssafy.enjoytrip.domain.board.service;

import com.ssafy.enjoytrip.domain.board.model.BoardDto;

import java.util.List;

public interface BoardService {
    void writeArticle(String subject, String content, String userId) throws Exception;

    List<BoardDto> listArticle(String keyword) throws Exception;

    List<BoardDto> search(String keyword) throws Exception;

    BoardDto detailArticle(int id) throws Exception;

}
