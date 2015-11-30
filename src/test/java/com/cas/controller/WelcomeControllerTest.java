package com.cas.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cas.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations={"classpath:ApplicationContext.xml"})
@WebAppConfiguration
public class WelcomeControllerTest {
 
    private MockMvc mockMvc;

 
    @Autowired
    private WebApplicationContext webApplicationContext;
    MockHttpSession session;
 
    @Before
    public void setUp() {

    	MockitoAnnotations.initMocks(this);
    	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).dispatchOptions(true).build();
    }

    @Test
    public void welcomePageWithoutLogin() throws Exception {
     
    	//This test indirect tests Authentication Interceptor when user tries to access certain page like /welcome without loggin in
        this.mockMvc.perform(get("/welcome"))
                .andExpect(status().is(302));
            
    }
    
    
    @Test
    public void welcomePageWithLogin() throws Exception {
     
    	
    	User user = new User();
    	//Populate the new user and add it to requestBuilder session.
    	
    	user.setEmailId("test@buffalo.edu");
    	user.setUsername("test");
    	user.setPassword("test@123");
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/welcome")
                .sessionAttr("LOGGEDIN_USER", user);
    	
    	this.mockMvc.perform(requestBuilder)
       .andExpect(status().isOk())
       .andExpect(view().name("welcomepage"))
       .andExpect(forwardedUrl("/WEB-INF/jsppages/welcomepage.jsp"));
    
            
    }
}