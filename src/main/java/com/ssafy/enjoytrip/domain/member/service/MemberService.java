package com.ssafy.enjoytrip.domain.member.service;

import com.ssafy.enjoytrip.domain.member.entity.Member;

public interface MemberService {
	int joinMember(Member memberDto) throws Exception;
	Member getMember(String userId, String userPassword) throws Exception;
	int deleteMember(String userId) throws Exception;
	int updateMemberInfo(Member memberDto) throws Exception;
	int updateMemberPassword(Member memberDto) throws Exception;
	String findPassword(String userId, String userName) throws Exception;
}
