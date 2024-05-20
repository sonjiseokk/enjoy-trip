package com.ssafy.enjoytrip.domain.board.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.domain.board.model.BannedBoardDto;
import com.ssafy.enjoytrip.domain.board.model.BoardDto;
import com.ssafy.enjoytrip.domain.board.controller.request.UpdateBoardDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
	void writeArticle(BoardDto boardDto) throws SQLException;
	List<BoardDto> listArticle(int boardType) throws SQLException;
	BoardDto findById(int id) throws SQLException;

	void viewCount(int id) throws SQLException;

	void update(UpdateBoardDto boardDto) throws SQLException;

	void delete(int id) throws SQLException;
	
	void writeBannedArticle(BannedBoardDto bannedBoardDto) throws SQLException;
	
	List<BannedBoardDto> listBannedArticle(String keyword) throws SQLException;
	
	BannedBoardDto findBanById(int id) throws SQLException;

	void viewBanCount(int id) throws SQLException;
}
