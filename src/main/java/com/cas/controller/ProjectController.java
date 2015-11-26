package com.cas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cas.delegate.ProjectDelegate;
import com.cas.delegate.RegisterDelegate;
import com.cas.model.Project;

@Controller
public class ProjectController {
	@Autowired
	private ProjectDelegate projectDelegate;

	@RequestMapping(value = "/getproject", method = RequestMethod.GET)
	public ModelAndView getProjectInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("welcomepage");
		Project project = new Project();
		model.addObject("project", project);
		return model;
	}

	@RequestMapping(value = "/project", method = RequestMethod.POST)
	public ModelAndView createProject(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("project") Project project) {
		ModelAndView model = null;
		try {
			
			boolean isProjectExists = projectDelegate.createProject(project);
			if (!isProjectExists) {
				System.out.println("Project creation Successful");
				/*User user = new User();
				user.setEmailId(user.getEmailId());
				model = new ModelAndView("register");
				request.setAttribute("message", "Succesfully registered!");
				model.addObject("user", user);*/
				
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
