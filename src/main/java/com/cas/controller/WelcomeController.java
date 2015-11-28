package com.cas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cas.delegate.TreeViewDelegate;
import com.cas.model.User;


@Controller
public class WelcomeController {
	@Autowired
	private TreeViewDelegate treeViewDelegate;

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView displayHomepage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("welcomepage");
		return model;
	}

	@RequestMapping(value = "/loadProfile", method = RequestMethod.POST)
	public @ResponseBody String displayTreeView(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("LOGGEDIN_USER");
		String emailId = user.getEmailId();
		JSONObject profile=null;
		try {
			profile = treeViewDelegate.populate(emailId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String profileString = null;
		if(profile != null){
			profileString = profile.toString();
		}
		System.out.println("My Profile is :"+profileString);
		return profileString;
	}
}
