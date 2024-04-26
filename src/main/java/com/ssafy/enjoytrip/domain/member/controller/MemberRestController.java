package com.ssafy.enjoytrip.domain.member.controller;

import com.ssafy.enjoytrip.domain.member.controller.request.LoginMemberDto;
import com.ssafy.enjoytrip.domain.member.controller.request.PasswordFindRequest;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import com.ssafy.enjoytrip.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberRestController {
    private static final Logger log = LoggerFactory.getLogger(MemberRestController.class);
    private final MemberService memberService;

    /**
     * 회원 정보를 입력받아 회원가입을 수행하는 메소드
     *
     * @param memberDto
     * @return
     * @throws Exception
     */
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberDto memberDto) throws Exception {
        memberService.joinMember(memberDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(HttpStatus.OK.value(), "회원가입 성공"));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginMemberDto dto, HttpSession session) throws Exception {
        MemberDto loginMemberDto = memberService.login(dto);
        session.setAttribute("userinfo", loginMemberDto);

        log.info(loginMemberDto.toString());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(HttpStatus.OK.value(), "성공적으로 로그인 되었습니다."));
    }

    /**
     * 회원정보를 입력받아 회원 정보를 업데이트 하는 메소드
     *
     * @param dto 회원 아이디, 회원 이름, 회원 비밀번호, 회원 이메일
     * @return
     * @throws Exception
     */
    @PatchMapping("/update/info")
    public ResponseEntity<?> updateMember(@RequestBody MemberDto dto, HttpSession session) throws Exception {
        MemberDto userinfo = (MemberDto) session.getAttribute("userinfo");
        dto.setUserId(userinfo.getUserId());

        memberService.updateMemberInfo(dto);

        session.removeAttribute("userinfo");
        MemberDto newUser = memberService.findMember(userinfo.getUserId());
        session.setAttribute("userinfo", newUser);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(HttpStatus.OK.value(), userinfo));
    }

    @PostMapping("/update/password")
    public ResponseEntity<?> updateMemberPassword(@RequestParam("userId") String userId, @RequestParam("userPassword") String userPassword) throws Exception {
        memberService.updateMemberPassword(userId, userPassword);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(HttpStatus.OK.value(), "비밀번호가 변경되었습니다."));
    }

    /**
     * 비밀번호 찾기
     * 추후 이메일 인증 로직 도입하여 위 메소드와 통합될 예정
     *
     * @param userId
     * @param userName
     * @return
     * @throws Exception
     */
    @PostMapping("/update/password/find")
    public ResponseEntity<?> updateMemberPasswordFind(
            @RequestBody PasswordFindRequest request) throws Exception {
        String password = memberService.findPassword(request.getUserId(), request.getUserName());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(HttpStatus.OK.value(), password));
    }


    /**
     * 회원 탈퇴 로직을 수행하는 메소드
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("/exit")
    public ResponseEntity<?> exit(@RequestParam("userId") String userId) throws Exception {
        memberService.deleteMember(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result<>(HttpStatus.OK.value(), "회원 데이터가 삭제되었습니다."));
    }

    @Data
    @Builder
    static class Result<T> {
        int status;
        T data;
    }
}
