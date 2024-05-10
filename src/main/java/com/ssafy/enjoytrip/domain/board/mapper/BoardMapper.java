package com.ssafy.enjoytrip.domain.board.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.domain.board.model.BoardDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
	void writeArticle(BoardDto boardDto) throws SQLException;
	List<BoardDto> listArticle() throws SQLException;
	BoardDto findById(int id) throws SQLException;

	void viewCount(int id) throws SQLException;
}
