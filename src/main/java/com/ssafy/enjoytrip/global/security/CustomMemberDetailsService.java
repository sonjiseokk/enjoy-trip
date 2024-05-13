package com.ssafy.enjoytrip.global.security;

import com.ssafy.enjoytrip.domain.member.mapper.MemberMapper;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {
    private final MemberMapper memberMapper;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        MemberDto findMember;
        try {
            findMember = memberMapper.findMemberById(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (findMember != null) {
            return new CustomMemberDetails(findMember);
        }
        return null;
    }
}