package com.ssafy.enjoytrip.domain.board.controller;

import com.ssafy.enjoytrip.domain.board.mapper.BoardMapper;
import com.ssafy.enjoytrip.domain.board.model.BoardDto;
import com.ssafy.enjoytrip.domain.board.model.BoardWriteRequest;
import com.ssafy.enjoytrip.domain.board.service.BoardService;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import com.ssafy.enjoytrip.domain.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRestControllerTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private BoardService boardService;

    @BeforeEach
    public void beforeEach() throws Exception {
        MemberDto memberDto = MemberDto.builder()
                .userId("ssafy")
                .userName("ssafy")
                .userPassword("1234")
                .userEmail("ssafy@ssafy.com")
                .build();
        memberDto.defaultRole();
        memberService.joinMember(memberDto);
    }
    @Test
    @DisplayName("글 작성 테스트")
    @Transactional
    void 글_작성_테스트() throws Exception {
        //given
        BoardWriteRequest request = new BoardWriteRequest("제목", "내용", "ssafy", 1);
        //when
        boardService.writeArticle(request);
        List<BoardDto> list = boardService.listArticle(1, "");

        //then
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getSubject()).isEqualTo(request.getSubject());
    }


}