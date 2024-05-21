package com.ssafy.enjoytrip.domain.board.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.domain.board.controller.request.TripBoardSearchDto;
import com.ssafy.enjoytrip.domain.board.controller.request.UpdateBoardDto;
import com.ssafy.enjoytrip.domain.board.model.BannedBoardDto;
import com.ssafy.enjoytrip.domain.board.model.BoardDto;

@Mapper
public interface BoardMapper {
	void writeArticle(BoardDto boardDto) throws SQLException;
	List<BoardDto> listArticle(int boardType) throws SQLException;
	BoardDto findById(int id) throws SQLException;
	String findUserIdByBoardId(int boardId) throws SQLException;
	void viewCount(int id) throws SQLException;

	void update(UpdateBoardDto boardDto) throws SQLException;

	void delete(int id) throws SQLException;
	
	void writeBannedArticle(BannedBoardDto bannedBoardDto) throws SQLException;
	
	List<BannedBoardDto> listBannedArticle(String keyword) throws SQLException;
	
	BannedBoardDto findBanById(int id) throws SQLException;

	void viewBanCount(int id) throws SQLException;
	
	List<BoardDto> listTripArticle(TripBoardSearchDto tripBoardSearchDto) throws SQLException;
}
