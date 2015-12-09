package com.cas.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cas.delegate.LoginDelegate;
import com.cas.model.User;
 

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations={"classpath:ApplicationContext.xml"})
@WebAppConfiguration
public class LoginControllerTest {
 
    private MockMvc mockMvc;
    @Autowired
    private LoginDelegate loginDelegate;
 
    @Autowired
    private WebApplicationContext webApplicationContext;
 
    @Before
    public void setUp() {

    	MockitoAnnotations.initMocks(this);
    	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).dispatchOptions(true).build();
    }

    @Test
    public void displayLoginPageTest() throws Exception {
     
 
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginpage"))
                .andExpect(forwardedUrl("/WEB-INF/jsppages/loginpage.jsp"));
            
    }
    
    @Test
    public void performLoginGetTest() throws Exception {
     
    	
        this.mockMvc.perform(get("/performlogin"))
                .andExpect(status().is(405));
    }
    
    @Test
    public void performLoginPostValidCredentialsTest() throws Exception {
     
    	User user = new User();
    	user.setEmailId("apimple@buffalo.edu");
    	user.setPassword("password");
    	User returnedUser = this.loginDelegate.isValidUser(user.getEmailId(), user.getPassword());
    	
    	assertNotNull(returnedUser);

           
    }
    
    @Test
    public void performLoginPostInvalidCredentialsTest() throws Exception {
     
    	User user = new User();
    	user.setEmailId("abc@buffalo.edu");
    	user.setPassword("password");
    	User returnedUser = this.loginDelegate.isValidUser(user.getEmailId(), user.getPassword());
    	
    	assertEquals(returnedUser, null);
  
           
    }
   
}