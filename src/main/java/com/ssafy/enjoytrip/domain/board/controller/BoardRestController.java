package com.ssafy.enjoytrip.domain.board.controller;

import com.ssafy.enjoytrip.domain.board.model.BannedBoardDto;
import com.ssafy.enjoytrip.domain.board.model.BoardDto;
import com.ssafy.enjoytrip.domain.board.model.WriteResponse;
import com.ssafy.enjoytrip.domain.board.controller.request.BoardWriteRequest;
import com.ssafy.enjoytrip.domain.board.controller.request.UpdateBoardDto;
import com.ssafy.enjoytrip.domain.board.service.BoardService;
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
public class BoardRestController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<?> list(@RequestParam("boardType") int boardType,@RequestParam(value = "keyword", required = false) String keyword) throws Exception {
        List<BoardDto> list = boardService.listArticle(boardType,keyword);
        if (!list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true,HttpStatus.OK.value(), list));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(false,HttpStatus.NOT_FOUND.value(), "등록된 게시물이 없습니다."));
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable("id") int id) throws Exception {
        BoardDto boardDto = boardService.detailArticle(id);
        if (boardDto != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true,HttpStatus.OK.value(), boardDto));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(false,HttpStatus.NOT_FOUND.value(), "해당 게시물이 없습니다."));
        }
    }

    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody BoardWriteRequest request) throws Exception {
    	int result = boardService.writeArticle(request);
    	WriteResponse writeResponse;
    	if(result < 0) {
    		 writeResponse = new WriteResponse(-1, "부적절한 게시글로 판단되어 차단되었습니다.");
    		
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true,HttpStatus.OK.value(), writeResponse));
    	}
    	writeResponse = new WriteResponse(1, "글 작성이 완료되었습니다.");
		
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(true,HttpStatus.OK.value(), writeResponse));
    }
    
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) throws Exception {
        boardService.deleteArticle(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(true,HttpStatus.OK.value(), "글 삭제가 완료되었습니다."));
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modify(@RequestBody UpdateBoardDto request) throws Exception {
    	boardService.updateArticle(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(true,HttpStatus.OK.value(), "글 작성이 완료되었습니다."));
    }
    
    @GetMapping("/banList/{keyword}")
    public ResponseEntity<?> listBanned(@PathVariable("keyword") String keyword) throws Exception {
        List<BannedBoardDto> list = boardService.listBannedArticle(keyword);
        System.out.println(keyword + " " + list.size());
        if (!list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true,HttpStatus.OK.value(), list));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(false,HttpStatus.NOT_FOUND.value(), "등록된 게시물이 없습니다."));
        }
    }
    
    @GetMapping("/ban/{id}")
    public ResponseEntity<?> banDetail(@PathVariable("id") int id) throws Exception {
        BannedBoardDto bannedBoardDto = boardService.detailBanArticle(id);
        if (bannedBoardDto != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true,HttpStatus.OK.value(), bannedBoardDto));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(false,HttpStatus.NOT_FOUND.value(), "해당 게시물이 없습니다."));
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

    @Data
    @Builder
    static class Result<T> {
        boolean success;
        int status;
        T data;
    }
}
