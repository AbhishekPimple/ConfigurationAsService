package com.cas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cas.delegate.LoginDelegate;
import com.cas.model.User;

@Controller
public class LoginController {
	@Autowired
	private LoginDelegate loginDelegate;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("loginpage");
		User user = new User();
		model.addObject("user", user);
		return model;
	}

	@RequestMapping(value = "/performlogin", method = RequestMethod.POST)
	public ModelAndView performLogin(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("user") User user) {
		ModelAndView model = null;
		try {
			boolean isValidUser = loginDelegate.isValidUser(user.getEmailId(), user.getPassword());
			if (isValidUser) {
				System.out.println("User Login Successful");
				request.setAttribute("loggedInUser", user.getEmailId());
				model = new ModelAndView("welcomepage");
			} else {
				model = new ModelAndView("loginpage");
				model.addObject("user", user);
				request.setAttribute("message", "Invalid credentials!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
}
