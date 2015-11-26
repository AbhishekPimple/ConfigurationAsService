package com.cas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cas.delegate.ProjectDelegate;
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
	public String createProject(@ModelAttribute(value="project") Project project, BindingResult result) {
		
		try {
			if(!result.hasErrors()){
				Project newProject = new Project();
				newProject = projectDelegate.createProject(project);
				if (newProject != null) {
					System.out.println("Project is created Successfully");
					return "success";
				} else {
					
				}
			}else{
				return "fail";
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

		
	}
}
