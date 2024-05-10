package com.ssafy.enjoytrip.domain.board.controller;

import com.ssafy.enjoytrip.domain.board.model.BoardDto;
import com.ssafy.enjoytrip.domain.board.service.BoardService;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import com.ssafy.enjoytrip.domain.trip.controller.TripController;
import com.ssafy.enjoytrip.domain.trip.model.TripDescriptionDto;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardRestController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(value = "keyword", required = false) String keyword) throws Exception {
        List<BoardDto> list = boardService.listArticle(keyword);
        if (!list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(HttpStatus.OK.value(), list));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(HttpStatus.NOT_FOUND.value(), "등록된 게시물이 없습니다."));
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable("id") int id) throws Exception {
        BoardDto boardDto = boardService.detailArticle(id);
        if (boardDto != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(HttpStatus.OK.value(), boardDto));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(HttpStatus.NOT_FOUND.value(), "해당 게시물이 없습니다."));
        }
    }

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
     * 게시물 검색을 실행하는 메소드
     * keyword를 파라미터로 받는다
     *
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
