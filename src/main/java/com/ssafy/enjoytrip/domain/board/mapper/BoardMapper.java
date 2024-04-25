package com.ssafy.enjoytrip.domain.board.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.domain.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
	void writeArticle(Board board) throws SQLException;
	List<Board> listArticle() throws SQLException;
}
