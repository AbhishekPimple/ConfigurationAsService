package com.cas.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.cas.delegate.ProjectDelegate;
import com.cas.model.Project;

@Controller
public class ProjectController {
	@Autowired
	private ProjectDelegate projectDelegate;

	@RequestMapping(value = "/getprojects", method = RequestMethod.GET)
	public ModelAndView getProjectInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("welcomepage");
		Project project = new Project();
		model.addObject("project", project);
		return model;
	}

	@RequestMapping(value = "/project", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	public @ResponseBody Project createProject(@RequestBody String projectJson) throws JsonParseException, JsonMappingException, IOException {

		Project project = new ObjectMapper().readValue(projectJson, Project.class);
		
		try {

			if (projectDelegate.createProject(project) != null) {
				System.out.println("Project is created Successfully");
				return project;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
