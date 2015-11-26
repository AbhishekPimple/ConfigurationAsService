package com.cas.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cas.delegate.FileDelegate;
import com.cas.model.FileContent;


@Controller
public class FileController {
	@Autowired
	FileDelegate filedelegate;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getfile", method = RequestMethod.GET)
	public ModelAndView getFile(HttpServletRequest request, HttpServletResponse response) {
		
		
		ModelAndView model = new ModelAndView("showfile");
		String fileId = request.getParameter("fileid");
		List<String> fileContent = new ArrayList<String>();
		int intfileId = 0;
		if(fileId != null){
			intfileId = Integer.parseInt(fileId);
		}
		System.out.println(intfileId);
		fileContent = filedelegate.getFile(intfileId);
		String filename = fileContent.get(0);
		fileContent.remove(0);
		
		
		model.addObject("filename", filename);
		//model.ad
		model.addObject("filecontent", fileContent);
		
		
		return model;
	}
	
	@RequestMapping(value = "/savefile", method = RequestMethod.POST)
	public ModelAndView saveFile(HttpServletRequest request, HttpServletResponse response, @RequestBody FileContent fileContent) {
		
		try {
			
			
			String name = fileContent.getName();
			String content = fileContent.getContent();
			System.out.println(name);
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
