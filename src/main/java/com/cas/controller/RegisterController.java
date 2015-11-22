package com.cas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cas.delegate.RegisterDelegate;
import com.cas.model.NewUser;
import com.cas.model.User;

@Controller
public class RegisterController {
	@Autowired
	private RegisterDelegate registerDelegate;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView displayRegistrationPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("register");
		NewUser newuser = new NewUser();
		model.addObject("newuser", newuser);
		return model;
	}

	@RequestMapping(value = "/performregister", method = RequestMethod.POST)
	public ModelAndView performRegistration(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("newuser") NewUser newUser) {
		ModelAndView model = null;
		try {
			
			boolean isUserExists = registerDelegate.register(newUser.getEmailId(), newUser.getUsername(), newUser.getPassword());
			if (!isUserExists) {
				System.out.println("User Registration Successful");
				User user = new User();
				user.setEmailId(newUser.getEmailId());
				model = new ModelAndView("loginpage");
				model.addObject("user", user);
				
			} else {
				model = new ModelAndView("register");
				request.setAttribute("message", "You have already registered!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
}
