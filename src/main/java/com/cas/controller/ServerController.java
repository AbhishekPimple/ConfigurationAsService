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

import com.cas.delegate.ServerDelegate;
import com.cas.model.Server;


@Controller
public class ServerController {
    @Autowired
    private ServerDelegate serverDelegate;
    private static final Logger LOGGER = Logger.getLogger(ServerController.class.getName());

    @RequestMapping(value = "/server", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public Server createServer(@RequestBody String serverJson) throws  IOException {

        Server server = new ObjectMapper().readValue(serverJson, Server.class);

        try {

            if (serverDelegate.createServer(server) != null) {
                return server;
            } else {
                return null;
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
        return null;

    }
}