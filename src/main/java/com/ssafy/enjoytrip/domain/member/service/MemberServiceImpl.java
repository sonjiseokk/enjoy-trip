package com.ssafy.enjoytrip.domain.member.service;

import com.ssafy.enjoytrip.domain.member.entity.Member;
import com.ssafy.enjoytrip.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberMapper memberMapper;
	@Override
	public int joinMember(Member memberDto) throws Exception {
		memberDto.setUserPassword(hashing(memberDto.getUserPassword()));
		memberMapper.joinMember(memberDto);
		return 0;
	}

	@Override
	public Member getMember(String userId, String userPassword) throws Exception {
		String newUserPassword = hashing(userPassword);
		return memberMapper.getMember(userId, newUserPassword);
	}

	@Override
	public int deleteMember(String userid) throws Exception {
		memberMapper.deleteMember(userid);
		return 0;
	}

	@Override
	public int updateMemberInfo(Member member) throws Exception {
		memberMapper.updateMemberInfo(member);
		return 0;
	}

	@Override
	public int updateMemberPassword(Member member) throws Exception {
		memberMapper.updateMemberPassword(member);
		return 0;
	}

	@Override
	public String findPassword(String userId, String userName) throws Exception {
		return memberMapper.findPassword(userId, userName);
	}
	
    public String hashing(String word) {
        byte[] b = word.getBytes();
        return byteArrayToString(Encrypt.hash(b));
    }
    public static String byteArrayToString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte data : bytes) {
            builder.append(String.format("%x", data));
        }
        String string = builder.toString();
        String string2 = string.substring(30);
        return string2;
    }

}
