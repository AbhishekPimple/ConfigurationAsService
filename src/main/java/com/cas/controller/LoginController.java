package com.cas.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cas.delegate.LoginDelegate;
import com.cas.model.User;

@Controller
public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    @Autowired
    private LoginDelegate loginDelegate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView displayLogin() {
        ModelAndView model = new ModelAndView("loginpage");
        User user = new User();
        model.addObject("user", user);
        return model;
    }

    @RequestMapping(value = "/performlogin", method = RequestMethod.POST)
    public String performLogin(HttpServletRequest request, 
            @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {

        try {
            User returnedUser = loginDelegate.isValidUser(user.getEmailId(), user.getPassword());
            if (returnedUser != null) {
                request.getSession().setAttribute("LOGGEDIN_USER", user);
                redirectAttributes.addFlashAttribute("loggedInUser", returnedUser.getUsername());
                return "redirect:/welcome";
            } else {
                redirectAttributes.addFlashAttribute("message", "Invalid credentials!");
                return "redirect:/";
            }

        }  catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);

        }
        return "redirect:/";

    }
}
