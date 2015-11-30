package com.cas.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

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
import com.fasterxml.jackson.core.JsonParseException;

@Controller
public class WorkbenchController {

    @Autowired
    private WorkbenchDelegate workbenchDelegate;
    private static final Logger LOGGER = Logger.getLogger(WorkbenchController.class.getName());

    @RequestMapping(value = "/workbench", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public Workbench createWorkbench(HttpServletRequest request,@RequestBody String workbenchJson) throws  IOException { 


        Workbench workbench = new ObjectMapper().readValue(workbenchJson, Workbench.class);
        User user = (User) request.getSession().getAttribute("LOGGEDIN_USER");
        String userId = user.getEmailId();
        try {

            if (workbenchDelegate.createWorkbench(workbench, userId) != null) {
                return workbench;
            } else {
                return null;
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
        return null;

    }
	@RequestMapping(value = "/workbenchupdate", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	public @ResponseBody Workbench updateWorkbench(@RequestBody String workbenchJson) throws JsonParseException, JsonMappingException, IOException {

		Workbench workbench = new ObjectMapper().readValue(workbenchJson, Workbench.class);

		try {

			if (workbenchDelegate.updateWorkbench(workbench) != null) {
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
