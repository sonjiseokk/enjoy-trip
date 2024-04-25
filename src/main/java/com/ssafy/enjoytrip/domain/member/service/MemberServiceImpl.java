package com.ssafy.enjoytrip.domain.member.service;

import com.ssafy.enjoytrip.domain.member.controller.request.LoginMemberDto;
import com.ssafy.enjoytrip.domain.member.controller.request.UpdateMemberDto;
import com.ssafy.enjoytrip.domain.member.entity.Member;
import com.ssafy.enjoytrip.domain.member.mapper.MemberMapper;
import com.ssafy.enjoytrip.global.util.Encrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;

    @Override
    public void joinMember(Member memberDto) throws Exception {
        memberDto.encryptPassword(hashing(memberDto.getUserPassword()));
        try {
            memberMapper.joinMember(memberDto);
        } catch (Exception e) {
            throw new Exception("회원가입에 실패하였습니다.");
        }
    }

    @Override
    public Member login(LoginMemberDto dto) throws Exception {
        String newUserPassword = hashing(dto.getLoginPw());

        Member member = memberMapper.findMemberById(dto.getLoginId());
        if (member.getUserId().equals(dto.getLoginId()) && member.getUserPassword().equals(newUserPassword)) {
            return member;
        } else {
            throw new Exception("로그인에 실패했습니다.");
        }
    }

    @Override
    public void deleteMember(String userid) throws Exception {
        try {
            if (memberMapper.findMemberById(userid) != null) {
                memberMapper.deleteMember(userid);
            }
        } catch (Exception e) {
            throw new Exception("회원 삭제에 실패했습니다.");
        }
    }

    @Override
    public Member updateMemberInfo(final String id, UpdateMemberDto dto) throws Exception {
        try {
            memberMapper.updateMemberInfo(id, dto);
            return memberMapper.findMemberById(id);
        } catch (Exception e) {
            throw new Exception("사용자를 업데이트하는데 실패했습니다.");
        }
    }

    @Override
    public void updateMemberPassword(String userId, String newPw) throws Exception {

        Member member = memberMapper.findMemberById(userId);
        if (member == null || newPw.isEmpty()) {
            throw new Exception("해당 유저가 없습니다.");
        }
        try {
            memberMapper.updateMemberPassword(userId, hashing(newPw));
        } catch (Exception e) {
            throw new Exception("비밀번호 업데이트에 실패했습니다.");
        }
    }

    @Override
    public String findPassword(String userId, String userName) throws Exception {
        Member member = memberMapper.findMemberById(userId);
        if (member.getUserId().equals(userId) && member.getUserName().equals(userName)) {
            return member.getUserPassword();
        }
        throw new Exception("비밀번호 찾기에 실패했습니다.");
    }

    @Override
    public boolean rememberMe(final String saveId) {
        return saveId.equals("saveid");
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
