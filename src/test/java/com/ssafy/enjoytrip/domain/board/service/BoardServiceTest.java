package com.ssafy.enjoytrip.domain.board.service;

import com.ssafy.enjoytrip.domain.board.mapper.BoardMapper;
import com.ssafy.enjoytrip.domain.board.model.BoardDto;
import com.ssafy.enjoytrip.domain.board.controller.request.BoardWriteRequest;
import com.ssafy.enjoytrip.domain.board.controller.request.UpdateBoardDto;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import com.ssafy.enjoytrip.domain.member.service.MemberService;
import com.ssafy.enjoytrip.global.exception.NotFoundArticleException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
class BoardServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardMapper boardMapper;

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
    @DisplayName("글 작성 및 조회 테스트")
    void 글_작성_및_조회_테스트() throws Exception {
        //given
        BoardWriteRequest request = new BoardWriteRequest("제목", "내용", "ssafy", 1);
        //when
        int id = boardService.writeArticle(request);
        log.info("id = {}", id);
        List<BoardDto> list = boardService.listArticle(1, "");

        //then
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getSubject()).isEqualTo(request.getSubject());
    }

    @Test
    @DisplayName("글 상세조회 테스트")
    void 글_상세조회_테스트() throws Exception {
        //given
        BoardWriteRequest request = new BoardWriteRequest("제목", "내용", "ssafy", 1);
        int id = boardService.writeArticle(request);
        //when

        BoardDto boardDto = boardService.detailArticle(id);
//        boardMapper.viewCount(id);

        //then
        assertThat(boardDto.getSubject()).isEqualTo(request.getSubject());
        assertThat(boardService.detailArticle(id).getViewCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("글 업데이트 테스트")
    void 글_업데이트_테스트() throws Exception {
        //given
        BoardWriteRequest request = new BoardWriteRequest("제목", "내용", "ssafy", 1);
        int id = boardService.writeArticle(request);

        //when
        UpdateBoardDto updateBoardDto = new UpdateBoardDto(id, "변경 제목", "변경 내용");
        boardService.updateArticle(updateBoardDto);
        //then
        BoardDto boardDto = boardService.detailArticle(id);
        assertThat(boardDto.getSubject()).isEqualTo(updateBoardDto.getSubject());
        assertThat(boardDto.getContent()).isEqualTo(updateBoardDto.getContent());
    }

    @Test
    @DisplayName("글 삭제 테스트")
    void 글_삭제_테스트() throws Exception {
        //given
        BoardWriteRequest request = new BoardWriteRequest("제목", "내용", "ssafy", 1);
        int id = boardService.writeArticle(request);

        //when
        boardService.deleteArticle(id);

        //then
        assertThat(boardService.listArticle(1, "").size()).isEqualTo(0);
        Assertions.assertThrows(NotFoundArticleException.class, () -> {
            boardService.detailArticle(id);
        });

    }
}