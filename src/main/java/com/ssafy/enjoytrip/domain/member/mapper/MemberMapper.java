package com.ssafy.enjoytrip.domain.member.mapper;

import com.ssafy.enjoytrip.domain.member.controller.request.UpdateMemberDto;
import com.ssafy.enjoytrip.domain.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface MemberMapper {
    void joinMember(Member member) throws SQLException;

    Member findMemberById(String userId) throws SQLException;

    void deleteMember(String userId) throws SQLException;

    void updateMemberInfo(String id, UpdateMemberDto dto) throws SQLException;

    void updateMemberPassword(String id,String password) throws SQLException;
}
