package com.cas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ErrorController {
    
    
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView model = new ModelAndView("error");
       
        model.addObject("message", redirectAttributes.getFlashAttributes().get("message"));
        return model;
    }
}
