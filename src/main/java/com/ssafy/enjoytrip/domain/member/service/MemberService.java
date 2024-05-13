package com.ssafy.enjoytrip.domain.member.service;

import com.ssafy.enjoytrip.domain.member.controller.request.LoginMemberDto;
import com.ssafy.enjoytrip.domain.member.mapper.MemberMapper;
import com.ssafy.enjoytrip.domain.member.model.JwtDto;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import com.ssafy.enjoytrip.global.security.jwt.JwtUtil;
import com.ssafy.enjoytrip.global.util.Encrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberMapper memberMapper;
    private final JwtUtil jwtUtil;

    public void joinMember(MemberDto memberDto) throws Exception {
        memberDto.encryptPassword(hashing(memberDto.getUserPassword()));
        try {
            memberMapper.joinMember(memberDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("회원가입에 실패하였습니다.");
        }
    }

    public JwtDto login(LoginMemberDto dto) throws Exception {
        String newUserPassword = hashing(dto.getLoginPw());

        MemberDto findMemberDto = memberMapper.findMemberById(dto.getLoginId());
        if (findMemberDto.getUserId().equals(dto.getLoginId()) && findMemberDto.getUserPassword().equals(newUserPassword)) {
            return jwtUtil.getJwtDto(new Date(System.currentTimeMillis()), findMemberDto);
        } else {
            throw new Exception("로그인에 실패했습니다.");
        }
    }

    public void deleteMember(String userid) throws Exception {
        try {
            if (memberMapper.findMemberById(userid) != null) {
                memberMapper.deleteMember(userid);
            }
        } catch (Exception e) {
            throw new Exception("회원 삭제에 실패했습니다.");
        }
    }

    public void updateMemberInfo(MemberDto dto) throws Exception {
        try {
            memberMapper.updateMemberInfo(dto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("사용자를 업데이트하는데 실패했습니다.");
        }
    }

    public void updateMemberPassword(String userId, String newPw) throws Exception {

        MemberDto memberDto = memberMapper.findMemberById(userId);
        if (memberDto == null || newPw.isEmpty()) {
            throw new Exception("해당 유저가 없습니다.");
        }
        try {
            memberMapper.updateMemberPassword(userId, hashing(newPw));
        } catch (Exception e) {
            throw new Exception("비밀번호 업데이트에 실패했습니다.");
        }
    }

    public String findPassword(String userId, String userName) throws Exception {
        try {
            MemberDto memberDto = memberMapper.findMemberById(userId);
            if (memberDto.getUserId().equals(userId) && memberDto.getUserName().equals(userName)) {
                return memberDto.getUserPassword();
            }
            throw new Exception("아이디와 이름이 일치하지 않습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("비밀번호 찾기에 실패했습니다.");
        }
    }

    public boolean rememberMe(final String saveId) {
        return saveId.equals("ok");
    }

    public MemberDto findMember(final String userId) throws Exception {
        try {
            return memberMapper.findMemberById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("사용자를 찾을 수 없습니다.");
        }
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
