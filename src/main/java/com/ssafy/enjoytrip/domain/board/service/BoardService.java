package com.ssafy.enjoytrip.domain.board.service;

import com.ssafy.enjoytrip.domain.board.model.BoardDto;
import com.ssafy.enjoytrip.domain.board.model.BoardWriteRequest;

import java.util.List;

public interface BoardService {
    void writeArticle(BoardWriteRequest request) throws Exception;

    List<BoardDto> listArticle(int boardType,String keyword) throws Exception;

    List<BoardDto> search(int boardType,String keyword) throws Exception;

    BoardDto detailArticle(int id) throws Exception;

}
