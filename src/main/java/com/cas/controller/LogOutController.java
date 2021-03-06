package com.cas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/logout")
public class LogOutController {

    @RequestMapping(method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpSession session) {
        request.getSession().removeAttribute("LOGGEDIN_USER");
        session.invalidate();
        return "redirect:/";
    }
}
