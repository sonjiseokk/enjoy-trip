package com.ssafy.enjoytrip.domain.member.service;

import com.ssafy.enjoytrip.domain.member.controller.request.LoginMemberDto;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;

public interface MemberService {
    void joinMember(MemberDto memberDto) throws Exception;

    MemberDto login(LoginMemberDto dto) throws Exception;

    void deleteMember(String userId) throws Exception;

    void updateMemberInfo(MemberDto memberDto) throws Exception;

    void updateMemberPassword(String userId, String newPw) throws Exception;

    String findPassword(String userId, String userName) throws Exception;

    boolean rememberMe(String saveId);

    MemberDto findMember(String userId) throws Exception;
}
