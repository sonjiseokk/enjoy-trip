package com.ssafy.enjoytrip.domain.board.service;

import java.util.List;

import com.ssafy.enjoytrip.domain.board.entity.Board;
import com.ssafy.enjoytrip.domain.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	private final BoardMapper boardMapper;

	@Override
	public void writeArticle(Board board) throws Exception {
		boardMapper.writeArticle(board);
	}

	@Override
	public List<Board> listArticle() throws Exception {
		return boardMapper.listArticle();
	}

	@Override
	public List<Board> search(String keyword) throws Exception {
		String[] keywords = keyword.split(" ");
		return Kmp.multiKmp(boardMapper.listArticle(), keywords);
	}



}
