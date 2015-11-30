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

import com.cas.delegate.RegisterDelegate;
import com.cas.model.User;

@Controller
public class RegisterController {
    @Autowired
    private RegisterDelegate registerDelegate;
    private static final Logger LOGGER = Logger.getLogger(RegisterController.class.getName());
    private static final String REGISTER = "register";
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView displayRegistrationPage() {
        ModelAndView model = new ModelAndView(REGISTER);
        User user = new User();
        model.addObject("user", user);
        return model;
    }

    @RequestMapping(value = "/performregister", method = RequestMethod.POST)
    public ModelAndView performRegistration(HttpServletRequest request,
            @ModelAttribute("user") User user) {
        ModelAndView model = null;
        try {

            boolean isUserExists = registerDelegate.register(user.getEmailId(), user.getUsername(), user.getPassword());
            if (!isUserExists) {
                User userNew = new User();
                userNew.setEmailId(user.getEmailId());
                model = new ModelAndView(REGISTER);
                request.setAttribute("message", "Succesfully registered!");
                model.addObject("user", userNew);

            } else {
                model = new ModelAndView(REGISTER);
                request.setAttribute("message", "You have already registered!");
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);

        }

        return model;
    }
}
