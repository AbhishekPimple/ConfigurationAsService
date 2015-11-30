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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations={"classpath:ApplicationContext.xml"})
@WebAppConfiguration
public class RegisterControllerTest {
 
    private MockMvc mockMvc;
 

 
    @Autowired
    private WebApplicationContext webApplicationContext;
 
    @Before
    public void setUp() {

    	MockitoAnnotations.initMocks(this);
    	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).dispatchOptions(true).build();
    }

    
    @Test
    public void displayRegistrationPageTest() throws Exception {
     
 
        this.mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(forwardedUrl("/WEB-INF/jsppages/register.jsp"));
            
    }
}