package com.ssafy.enjoytrip.domain.member.controller;

import com.ssafy.enjoytrip.domain.member.controller.request.LoginMemberDto;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import com.ssafy.enjoytrip.domain.member.service.MemberService;
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
     * @param memberDto
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/join")
    public String join(@ModelAttribute MemberDto memberDto, Model model) throws Exception {
        memberService.joinMember(memberDto);
        return "redirect:/";
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
                        HttpSession session,
                        Model model) throws Exception {

        MemberDto loginMemberDto = memberService.login(dto);
        session.setAttribute("userinfo", loginMemberDto);

        if (memberService.rememberMe(saveId)) {
            session.setAttribute("idck","ok");
            session.setAttribute("saveid",loginMemberDto.getUserId());
        }

        return "index";
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

        return "index";
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
    public String updateMember(@ModelAttribute MemberDto dto, HttpSession session) throws Exception {
        MemberDto userinfo = (MemberDto) session.getAttribute("userinfo");

        memberService.updateMemberInfo(dto);

        MemberDto member = memberService.findMember(userinfo.getUserId());
        session.removeAttribute("userinfo");
        session.setAttribute("userinfo", member);

        return "redirect:/";
    }

    @PostMapping("/update/password")
    public String updateMemberPassword(@RequestParam("newPw") String newPw,
                                       HttpSession session, Model model) throws Exception {
        MemberDto userinfo = (MemberDto) session.getAttribute("userinfo");

        memberService.updateMemberPassword(userinfo.getUserId(), newPw);
        return "redirect:/";
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
        String password = memberService.findPassword(userId, userName);

        return "redirect:/";
    }

    /**
     * 회원 탈퇴 로직을 수행하는 메소드
     *
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping("/exit")
    public String exit(HttpSession session) throws Exception {
        MemberDto userinfo = (MemberDto) session.getAttribute("userinfo");
        memberService.deleteMember(userinfo.getUserId());
        return "redirect:/";
    }
}
