package com.ssafy.enjoytrip.domain.member.mapper;

import com.ssafy.enjoytrip.domain.member.controller.request.UpdateMemberDto;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface MemberMapper {
    void joinMember(MemberDto memberDto) throws SQLException;

    MemberDto findMemberById(String userId) throws SQLException;

    void deleteMember(String userId) throws SQLException;

    void updateMemberInfo(String id, UpdateMemberDto dto) throws SQLException;

    void updateMemberPassword(String id,String password) throws SQLException;
}
