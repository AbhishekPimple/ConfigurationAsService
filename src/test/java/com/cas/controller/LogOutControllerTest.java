package com.cas.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.RedirectView;

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
       .andExpect(status().is(302))
       .andExpect(redirectedUrlPattern("/"));
            
        
            
    }
    
    /* Ref:http://stackoverflow.com/questions/17834034/spring-mockmvc-redirectedurl-with-pattern     
*/    
    private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
        return new ResultMatcher() {
            public void match(MvcResult result) {
                Pattern pattern = Pattern.compile("/");
                assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
            }
        };
    }
    
   
   
}