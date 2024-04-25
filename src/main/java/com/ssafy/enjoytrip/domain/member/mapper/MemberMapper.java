package com.ssafy.enjoytrip.domain.member.mapper;

import com.ssafy.enjoytrip.domain.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface MemberMapper {
	int joinMember(Member memberDto) throws SQLException;
	Member getMember(String userId, String userPassword)throws SQLException;
	int deleteMember(String userId) throws SQLException;
	int updateMemberInfo(Member memberDto) throws SQLException;
	int updateMemberPassword(Member memberDto) throws SQLException;
	String findPassword(String userId, String userName) throws SQLException;
}
