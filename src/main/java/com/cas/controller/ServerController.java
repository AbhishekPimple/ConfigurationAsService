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

import com.cas.delegate.ServerDelegate;
import com.cas.model.Server;

@Controller
public class ServerController {
	@Autowired
	private ServerDelegate serverDelegate;

	@RequestMapping(value = "/server", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	public @ResponseBody Server createServer(@RequestBody String serverJson) throws JsonParseException, JsonMappingException, IOException {

		Server server = new ObjectMapper().readValue(serverJson, Server.class);

		try {

			if (serverDelegate.createServer(server) != null) {
				System.out.println("Server is created Successfully");
				return server;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}