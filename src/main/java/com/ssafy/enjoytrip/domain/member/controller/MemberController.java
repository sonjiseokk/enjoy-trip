package com.ssafy.enjoytrip.domain.member.controller;

import com.ssafy.enjoytrip.domain.member.controller.request.LoginMemberDto;
import com.ssafy.enjoytrip.domain.member.controller.request.UpdateMemberDto;
import com.ssafy.enjoytrip.domain.member.entity.Member;
import com.ssafy.enjoytrip.domain.member.service.MemberService;
import com.ssafy.enjoytrip.global.util.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원 정보를 입력받아 회원가입을 수행하는 메소드
     *
     * @param member
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/join")
    public String join(@ModelAttribute Member member, Model model) throws Exception {
        memberService.joinMember(member);
        return "/";
    }

    /**
     * 폼 데이터를 입력받아 로그인을 수행하는 메소드
     *
     * @param dto
     * @param saveId   리멤버미 태그
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public String login(@ModelAttribute LoginMemberDto dto,
                        @RequestParam(value = "saveid", required = false) String saveId,
                        HttpServletRequest request, HttpServletResponse response,
                        Model model) throws Exception {

        Member loginMember = memberService.login(dto);
        model.addAttribute("userinfo", loginMember);

        if (memberService.rememberMe(saveId)) {
            CookieUtil.logic(saveId, request, response);
        }

        return "/";
    }

    /**
     * 로그아웃 메소드
     *
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userinfo");
        session.invalidate();
        return "/";
    }

    /**
     * 회원정보를 입력받아 회원 정보를 업데이트 하는 메소드
     * 기존의 세션에 저장된 회원 정보를 삭제하고 업데이트된 회원 정보를 다시 세션에 저장한다.
     *
     * @param dto 회원 아이디, 회원 비밀번호, 회원 이메일
     * @return
     * @throws Exception
     */
    @PatchMapping("/update/info")
    public String updateMember(@ModelAttribute UpdateMemberDto dto, HttpSession session) throws Exception {
        Member userinfo = (Member) session.getAttribute("userinfo");

        Member updatedMember = memberService.updateMemberInfo(userinfo.getUserId(), dto);
        session.removeAttribute("userinfo");
        session.setAttribute("userinfo", updatedMember);

        return "/";
    }

    @PostMapping("/update/password")
    public String updateMemberPassword(@RequestParam("password") String password, HttpSession session, Model model) throws Exception {
        Member userinfo = (Member) session.getAttribute("userinfo");

        memberService.updateMemberPassword(userinfo.getUserId(), password);
        return "/";
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
    public String updateMemberPasswordFind(@RequestParam("userId") String userId, @RequestParam("userName") String userName) throws Exception {
        memberService.findPassword(userId, userName);
        return "/";
    }

    /**
     * 회원 탈퇴 로직을 수행하는 메소드
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping("/exit")
    public String exit(HttpSession session) throws Exception {
        Member userinfo = (Member) session.getAttribute("userinfo");
        memberService.deleteMember(userinfo.getUserId());
        return "/";
    }
}
