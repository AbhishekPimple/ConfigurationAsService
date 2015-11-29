package com.cas.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cas.delegate.ProjectDelegate;
import com.cas.model.Project;


@Controller
public class ProjectController {
    @Autowired
    private ProjectDelegate projectDelegate;
    private  static final Logger LOGGER = Logger.getLogger(ProjectController.class.getName()); 


    @RequestMapping(value = "/getprojects", method = RequestMethod.GET)
    public ModelAndView getProjectInfo() {
        ModelAndView model = new ModelAndView("welcomepage");
        Project project = new Project();

        model.addObject("project", project);
        return model;
    }

    @RequestMapping(value = "/project", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public  Project createProject(@RequestBody String projectJson) throws  IOException {

        Project project = new ObjectMapper().readValue(projectJson, Project.class);

        try {

            if (projectDelegate.createProject(project) != null) {
                return project;
            } else {
                return null;
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
        return null;

    }
}
