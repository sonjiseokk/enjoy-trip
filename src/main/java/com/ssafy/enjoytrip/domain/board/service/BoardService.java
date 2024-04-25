package com.ssafy.enjoytrip.domain.board.service;

import com.ssafy.enjoytrip.domain.board.entity.Board;

import java.util.List;

public interface BoardService {
	void writeArticle(Board board) throws Exception;
	List<Board> listArticle() throws Exception;
	List<Board> search(String keyword) throws Exception;

}
