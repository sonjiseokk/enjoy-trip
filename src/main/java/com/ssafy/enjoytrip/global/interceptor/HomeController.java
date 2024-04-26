package com.ssafy.enjoytrip.global.interceptor;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String root(HttpSession session, HttpServletResponse response, Model model) {
        String idck = (String) session.getAttribute("idck");
        String saveid = (String) session.getAttribute("saveid");
        if (idck != null && saveid != null) {
            model.addAttribute("idck", idck);
            model.addAttribute("saveid", saveid);
        } else {
            session.setAttribute("idck","no");
        }
        return "index";
    }
}
