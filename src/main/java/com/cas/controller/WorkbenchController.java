package com.cas.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cas.delegate.WorkbenchDelegate;
import com.cas.model.User;
import com.cas.model.Workbench;

@Controller
public class WorkbenchController {
	@Autowired
	private WorkbenchDelegate workbenchDelegate;
	
	@RequestMapping(value = "/workbench", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	public @ResponseBody Workbench createWorkbench(HttpServletRequest request,@RequestBody String workbenchJson) throws JsonParseException, JsonMappingException, IOException { 
	

	Workbench workbench = new ObjectMapper().readValue(workbenchJson, Workbench.class);
	User user = (User) request.getSession().getAttribute("LOGGEDIN_USER");
	String userId = user.getEmailId();
		try {

			if (workbenchDelegate.createWorkbench(workbench, userId) != null) {
				System.out.println("Workbench is created Successfully");
				return workbench;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
}