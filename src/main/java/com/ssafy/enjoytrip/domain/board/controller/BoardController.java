package com.ssafy.enjoytrip.domain.board.controller;

import com.ssafy.enjoytrip.domain.board.entity.Board;
import com.ssafy.enjoytrip.domain.board.service.BoardService;
import com.ssafy.enjoytrip.domain.board.service.BoardServiceImpl;
import com.ssafy.enjoytrip.domain.member.entity.Member;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;


/**
 * Servlet implementation class BoardController
 */

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/write")
    public String mvwrite() {
        return "boardwrite";
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        root = request.getContextPath();
        String path = "";
        switch (action) {
            case "write":
                path = write(request, response);
                forward(request, response, path);
                break;
            case "mvlist":
                path = mvlist(request, response);
                forward(request, response, path);
                break;
            case "mvregister":
                path = mvregister(request, response);
                forward(request, response, path);
                break;
            case "register":
                path = register(request, response);
                redirect(request, response, path);
                break;
            case "search":
                path = search(request, response);
                forward(request, response, path);
                break;
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword", required = false) String keyword, Model model) throws Exception {
        List<Board> boardList = boardService.search(keyword);
        model.addAttribute("boardList", boardList);
        return "board";
    }

    private String register(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("userinfo");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");

        Board dto = new Board();
        dto.setSubject(subject);
        dto.setContent(content);
        dto.setUsername(member.getUserName());
        try {
            boardService.writeArticle(dto);
            return "/board?action=mvlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "/index.jsp";
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


    private String mvregister(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
        if (memberDto != null) {
            try {
                return "/boardWrite.jsp";
            } catch (Exception e) {
                e.printStackTrace();
                return "/index.jsp";
            }
        } else
            return "/login.jsp";
    }


    private String mvlist(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
        try {
            List<Board> list = boardService.listArticle();
            request.setAttribute("boardList", list);
            System.out.println(list.size());
            return "/board.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "/index.jsp";
        }
    }

    private String write(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
        if (memberDto != null) {
            Board board = new Board();
            board.setUsername(memberDto.getUserName());
            board.setSubject(request.getParameter("subject"));
            board.setContent(request.getParameter("content"));
            try {
                boardService.writeArticle(board);
                return "/board?action=mvlist";
            } catch (Exception e) {
                e.printStackTrace();
                return "/index.jsp";
            }
        } else
            return "/login.jsp";
    }


}
