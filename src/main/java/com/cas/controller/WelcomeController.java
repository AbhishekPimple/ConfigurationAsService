package com.cas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class WelcomeController {
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView displayHomepage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("welcomepage");
		return model;
	}
}
