package com.ssafy.enjoytrip.domain.member.service;

import com.ssafy.enjoytrip.domain.member.controller.request.LoginMemberDto;
import com.ssafy.enjoytrip.domain.member.controller.request.UpdateMemberDto;
import com.ssafy.enjoytrip.domain.member.entity.Member;

public interface MemberService {
    void joinMember(Member member) throws Exception;

    Member login(LoginMemberDto dto) throws Exception;

    void deleteMember(String userId) throws Exception;

    Member updateMemberInfo(final String id, UpdateMemberDto memberDto) throws Exception;

    void updateMemberPassword(String userId, String newPw) throws Exception;

    String findPassword(String userId, String userName) throws Exception;

    boolean rememberMe(String saveId);
}
