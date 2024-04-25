package com.ssafy.enjoytrip.domain.member.controller;

import com.ssafy.enjoytrip.domain.member.mapper.MemberMapper;
import com.ssafy.enjoytrip.domain.member.service.MemberService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    public String mvjoin(Model model) {
        return "join";
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        String path = "";
        switch (action) {
            case "join":
                path = join(request, response);
                forward(request, response, path);
                break;
            case "login":
                path = login(request, response);
                forward(request, response, path);
                break;
            case "logout":
                path = logout(request, response);
                forward(request, response, path);
                break;
            case "updateInfo":
                path = updateInfo(request, response);
                forward(request, response, path);
                break;
            case "updatePassword":
                path = updatePassword(request, response);
                forward(request, response, path);
                break;
            case "findPassword":
                path = findPassword(request, response);
                forward(request, response, path);
                break;
            case "signout":
                path = signout(request, response);
                forward(request, response, path);
                break;
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        doGet(request, response);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
        response.sendRedirect(request.getContextPath() + path);
    }

    private String join(HttpServletRequest request, HttpServletResponse response) {
        MemberDto newMember = new MemberDto();
        newMember.setUserId(request.getParameter("join-id"));
        newMember.setUserPassword(request.getParameter("join-pw"));
        newMember.setUserName(request.getParameter("join-name"));
        newMember.setUserEmail(request.getParameter("join-email"));

        try {
            MemberDto memberDto = memberService.getMember(newMember.getUserId(), newMember.getUserPassword());

            if (memberDto == null) {
                memberService.joinMember(newMember);
                return "/index.jsp";
            } else {
                request.setAttribute("msg", "사용가능한 아이디가 아닙니다.");
                return "/alert/msg.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String login(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("login-id");
        String userPwd = request.getParameter("login-pw");
        try {
            MemberDto memberDto = memberService.getMember(userId, userPwd);
            if (memberDto != null) {
//				session 설정
                HttpSession session = request.getSession();
                session.setAttribute("userinfo", memberDto);

//				cookie 설정
                String idsave = request.getParameter("saveid");
                if ("ok".equals(idsave)) { // 아이디 저장을 체크 했다면.
                    Cookie cookie = new Cookie("ssafy_id", userId);
                    cookie.setPath(request.getContextPath());
//					크롬의 경우 400일이 최대
//					https://developer.chrome.com/blog/cookie-max-age-expires/
                    cookie.setMaxAge(60 * 60 * 24 * 365 * 40); // 40년간 저장.
                    response.addCookie(cookie);
                } else { // 아이디 저장을 해제 했다면.
                    Cookie cookies[] = request.getCookies();
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            if ("ssafy_id".equals(cookie.getName())) {
                                cookie.setMaxAge(0);
                                response.addCookie(cookie);
                                break;
                            }
                        }
                    }
                }
                return "/index.jsp";
            } else {
                request.setAttribute("msg", "아이디 또는 비밀번호 확인 후 다시 로그인하세요.");
                return "/alert/msg.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("userinfo");
        session.invalidate();
        return "/index.jsp";
    }

    private String updateInfo(HttpServletRequest request, HttpServletResponse response) {
        MemberDto memberDto = new MemberDto();
        memberDto.setUserId(request.getParameter("mypage-id"));
        memberDto.setUserName(request.getParameter("mypage-name"));
        memberDto.setUserEmail(request.getParameter("mypage-email"));

        try {
            memberService.updateMemberInfo(memberDto);

            HttpSession session = request.getSession();
            session.setAttribute("userinfo", memberDto);

            return "/index.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String updatePassword(HttpServletRequest request, HttpServletResponse response) {
        MemberDto memberDto = new MemberDto();
        memberDto.setUserId(request.getParameter("pw-change-id"));
        memberDto.setUserPassword(request.getParameter("pw-change-new-pw"));

        String curPw = request.getParameter("pw-change-cur-pw");

        try {
            if (memberService.getMember(memberDto.getUserId(), curPw) != null) {
                memberService.updateMemberPassword(memberDto);
                return logout(request, response);
            } else {
                request.setAttribute("msg", "현재 비밀번호를 다시 확인해주세요");
                return "/alert/msg.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String findPassword(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("find-pw-id");
        String name = request.getParameter("find-pw-name");

        try {
            String foundPassword = memberService.findPassword(id, name);
            if (memberService.findPassword(id, name) != null) {
                request.setAttribute("msg", "비밀번호는 [ " + foundPassword + " ]입니다.");
                return "/alert/msg.jsp";
            } else {
                request.setAttribute("msg", "입력이 잘못되었습니다.");
                return "/alert/msg.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String signout(HttpServletRequest request, HttpServletResponse response) {
        try {
            String pw = request.getParameter("signout-pw");

            HttpSession session = request.getSession();
            MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");

            MemberDto dbMember = memberService.getMember(memberDto.getUserId(), pw);

            if (dbMember != null) {
                memberService.deleteMember(memberDto.getUserId());

                session.removeAttribute("userinfo");
                session.invalidate();

                return "/index.jsp";
            } else {
                request.setAttribute("msg", "현재 비밀번호를 다시 확인해주세요");
                return "/alert/msg.jsp";
            }
        } catch (Exception e) {
            return "";
        }
    }
}
