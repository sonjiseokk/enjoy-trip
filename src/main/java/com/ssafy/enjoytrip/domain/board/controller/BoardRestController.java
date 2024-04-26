package com.ssafy.enjoytrip.domain.board.controller;

import com.ssafy.enjoytrip.domain.board.model.BoardDto;
import com.ssafy.enjoytrip.domain.board.service.BoardService;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardRestController {
    private final BoardService boardService;

    /**
     * 글 작성을 진행하는 메소드
     * 세션에서 회원 정보를 가져온다
     *
     * @param subject 글 제목
     * @param content 글 내용
     * @param session 세션
     * @return 작성된 글이 있는 리스트
     * @throws Exception 서비스 예외 내용
     */
    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestParam("subject") String subject,
                                @RequestParam("content") String content,
                                HttpSession session) throws Exception {
        MemberDto userinfo = (MemberDto) session.getAttribute("userinfo");
        boardService.writeArticle(subject, content, userinfo.getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(HttpStatus.OK.value(), "글 작성이 완료되었습니다."));
    }

    /**
     * 글 리스트를 받아오는 메소드
     * TODO: 만약 isEmpty가 null을 인식 못한다면 수정해주어야함
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public String list(Model model) throws Exception {
        List<BoardDto> list = boardService.listArticle("");
        model.addAttribute("boardList", list);
        return "board";
    }

    /**
     * 게시물 검색을 실행하는 메소드
     * keyword를 파라미터로 받는다
     * @param keyword
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword", required = false) String keyword, Model model) throws Exception {
        List<BoardDto> boardDtoList = boardService.listArticle(keyword);
        model.addAttribute("boardList", boardDtoList);
        return "board";
    }
        @Data
    @Builder
    static class Result<T> {
        int status;
        T data;
    }
}
