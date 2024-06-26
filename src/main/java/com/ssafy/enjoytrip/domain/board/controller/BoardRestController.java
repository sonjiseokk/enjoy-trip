package com.ssafy.enjoytrip.domain.board.controller;

import com.ssafy.enjoytrip.domain.board.model.BannedBoardDto;
import com.ssafy.enjoytrip.domain.board.model.BoardDto;
import com.ssafy.enjoytrip.domain.board.model.WriteResponse;
import com.ssafy.enjoytrip.domain.board.controller.request.BoardWriteRequest;
import com.ssafy.enjoytrip.domain.board.controller.request.UpdateBoardDto;
import com.ssafy.enjoytrip.domain.board.service.BoardService;
import com.ssafy.enjoytrip.global.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class BoardRestController {
    private final BoardService boardService;
    private final JwtUtil jwtUtil;

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam("boardType") int boardType, @RequestParam(value = "contentId", required = false) Integer contentId, @RequestParam(value = "keyword", required = false) String keyword) throws Exception {
        List<BoardDto> list;
    	if(contentId == null) {
    		list = boardService.listArticle(boardType, keyword);
        }
    	else {    		
    		list = boardService.listTripArticle(boardType, contentId, keyword);
    	}
        if (!list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true, HttpStatus.OK.value(), list));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(false, HttpStatus.NOT_FOUND.value(), "등록된 게시물이 없습니다."));
        }
    }

    @GetMapping("/qna/detail/{id}")
    public ResponseEntity<?> qnaDetail(HttpServletRequest request, @PathVariable("id") int id) throws Exception {

        BoardDto boardDto = boardService.detailQna(id);
        if (boardDto != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true, HttpStatus.OK.value(), boardDto));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(false, HttpStatus.BAD_REQUEST.value(), "해당 게시물이 없습니다."));
        }
    }

    @GetMapping("/notice/detail/{id}")
    public ResponseEntity<?> noticeDetail(@PathVariable("id") int id) throws Exception {
        BoardDto boardDto = boardService.detailArticle(id);
        if (boardDto != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true, HttpStatus.OK.value(), boardDto));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(false, HttpStatus.BAD_REQUEST.value(), "해당 게시물이 없습니다."));
        }
    }

    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody BoardWriteRequest request) throws Exception {
        int result = boardService.writeArticle(request);
        WriteResponse writeResponse;
        if (result < 0) {
            writeResponse = new WriteResponse(-1, "부적절한 게시글로 판단되어 차단되었습니다.");
        }
        else writeResponse = new WriteResponse(1, "글 작성이 완료되었습니다.");

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(true, HttpStatus.OK.value(), writeResponse));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) throws Exception {
        boardService.deleteArticle(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(true, HttpStatus.OK.value(), "글 삭제가 완료되었습니다."));
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modify(@RequestBody UpdateBoardDto request) throws Exception {
        int response = boardService.updateArticle(request);
        WriteResponse writeResponse;
        if (response < 0) {
            writeResponse = new WriteResponse(-1, "부적절한 표현이 감지되어 수정할 수 없습니다.");
        }
        else writeResponse = new WriteResponse(1, "글 작성이 완료되었습니다.");
        
        return ResponseEntity.status(HttpStatus.OK)
        		.body(new Result<>(true, HttpStatus.OK.value(), writeResponse));
    }

    @GetMapping("/banList/{keyword}")
    public ResponseEntity<?> listBanned(@PathVariable("keyword") String keyword) throws Exception {
        List<BannedBoardDto> list = boardService.listBannedArticle(keyword);
        System.out.println(keyword + " " + list.size());
        if (!list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true, HttpStatus.OK.value(), list));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(false, HttpStatus.NOT_FOUND.value(), "등록된 게시물이 없습니다."));
        }
    }

    @GetMapping("/ban/{id}")
    public ResponseEntity<?> banDetail(@PathVariable("id") int id) throws Exception {
        BannedBoardDto bannedBoardDto = boardService.detailBanArticle(id);
        if (bannedBoardDto != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true, HttpStatus.OK.value(), bannedBoardDto));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(false, HttpStatus.NOT_FOUND.value(), "해당 게시물이 없습니다."));
        }
    }

//    /**
//     * 게시물 검색을 실행하는 메소드
//     * keyword를 파라미터로 받는다
//     *
//     * @param keyword
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/search")
//    public String search(@RequestParam(value = "keyword", required = false) String keyword, Model model) throws Exception {
//        List<BoardDto> boardDtoList = boardService.listArticle(keyword);
//        model.addAttribute("boardList", boardDtoList);
//        return "board";
//    }
    private String getUserId(final HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token = authorization.split(" ")[1];
        return jwtUtil.getUserId(token);
    }

    @Data
    @Builder
    static class Result<T> {
        boolean success;
        int status;
        T data;
    }
}
