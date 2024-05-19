package com.ssafy.enjoytrip.domain.like.controller;

import com.ssafy.enjoytrip.domain.like.service.LikeService;
import com.ssafy.enjoytrip.domain.trip.model.AttractionInfoDto;
import com.ssafy.enjoytrip.global.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class LikeRestController {
    private final LikeService likeService;
    private final JwtUtil jwtUtil;

    @GetMapping("/mylike")
    public ResponseEntity<?> myLikes(HttpServletRequest request) throws Exception {
        String userId = getUserId(request);

        List<AttractionInfoDto> myLikes = likeService.listLike(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(true, HttpStatus.OK.value(), myLikes));
    }

    @GetMapping("/like/{contentId}")
    public ResponseEntity<?> isLiked(@PathVariable("contentId") int contentId, HttpServletRequest request) throws Exception {

        String userId = getUserId(request);

        if (likeService.likeCheck(contentId, userId)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<>(true, HttpStatus.OK.value(), true));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(true, HttpStatus.OK.value(), false));
    }

    @PostMapping("/like/{contentId}")
    public ResponseEntity<?> getLike(@PathVariable("contentId") int contentId, HttpServletRequest request) throws Exception {

        String userId = getUserId(request);

        likeService.registLike(userId, contentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(true, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
    }

    @DeleteMapping("/like/{contentId}")
    public ResponseEntity<?> removeLike(@PathVariable("contentId") int contentId, HttpServletRequest request) throws Exception {
        String userId = getUserId(request);
        likeService.deleteLike(contentId,userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(true, HttpStatus.OK.value(), "좋아요 삭제 완료"));
    }

    //    @PostMapping(value = "/optimalpath")
//	public ResponseEntity<?> optimalPath(HttpServletRequest request) throws Exception {
//
//        String userId = getUserId(request);
//
//		List<AttractionInfoDto> path = likeService.optimalPath(userId,contentId);
//		if(path != null && !path.isEmpty()) {
//			return new ResponseEntity<>(path, HttpStatus.OK);
//		}
//		else {
//			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//		}
//
//	}
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
