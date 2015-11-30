package com.cas.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

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
    private static final Logger LOGGER = Logger.getLogger(WelcomeController.class.getName());

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView displayHomepage(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("welcomepage");
        User user = (User) request.getSession().getAttribute("LOGGEDIN_USER");
        model.addObject("loggedInUser", user.getUsername());
        return model;
    }

    @RequestMapping(value = "/loadProfile", method = RequestMethod.POST)
    @ResponseBody
    public String displayTreeView(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("LOGGEDIN_USER");
        String emailId = user.getEmailId();
        JSONObject profile=null;
        try {
            profile = treeViewDelegate.populate(emailId);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
        String profileString = null;
        if(profile != null){
            profileString = profile.toString();
        }
        return profileString;
    }
}
