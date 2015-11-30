package com.cas.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertNotNull;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LogOutControllerTest {
 
    private MockMvc mockMvc;
 
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    User user;
 
    @Before
    public void setUp() {
        user = new User();
        //Populate the new user and add it to requestBuilder session.
        
        user.setEmailId("test@buffalo.edu");
        user.setUsername("test");
        user.setPassword("test@123");
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).dispatchOptions(true).build();
    }

    @Test
    public void logoutTest() throws Exception {
     
 
        assertNotNull(user);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/logout")
                .sessionAttr("LOGGEDIN_USER", user);
        
        this.mockMvc.perform(requestBuilder)
       .andExpect(status().isOk())
       .andExpect(view().name("loginpage"))
       .andExpect(forwardedUrl("/WEB-INF/jsppages/loginpage.jsp"));
    
            
        
            
    }
    
   
   
}