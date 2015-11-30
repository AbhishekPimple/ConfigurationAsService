package com.cas.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import com.cas.dao.FileDao;
 

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations={"classpath:ApplicationContext.xml"})
@WebAppConfiguration
public class FileServiceTest {
 
    private MockMvc mockMvc;
    @Autowired
    private FileDao fileDao;
    
    private int existingFileId;
    private int nonexistingFileId;
    
 
    @Autowired
    private WebApplicationContext webApplicationContext;
 
    @Before
    public void setUp() {
    	this.existingFileId = 13;
    	this.nonexistingFileId = 9999;
    	MockitoAnnotations.initMocks(this);
    	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).dispatchOptions(true).build();
    }

    @Test
    public void getFileTest() throws Exception {
    	assertNotNull(fileDao.getFileData(existingFileId));
    	//assertEquals(null, fileDao.getFileData(nonexistingFileId));
    }
   
}