package com.cas.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;


import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cas.delegate.FileDelegate;
import com.cas.model.File;
import com.cas.model.FileContent;




@Controller
public class FileController {
    @Autowired
    FileDelegate filedelegate;
    private  static final Logger LOGGER = Logger.getLogger(FileController.class.getName()); 
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getfile", method = RequestMethod.GET)
    public ModelAndView getFile(HttpServletRequest request) {
        ModelAndView model = null;
        String fileId = request.getParameter("fileid");
        List<String> fileContent ;
        int intfileId = 0;
        if(fileId != null){
            intfileId = Integer.parseInt(fileId);
        }
        fileContent = filedelegate.getFile(intfileId);
        if(fileContent == null){
            model = new ModelAndView("error");
            model.addObject("message", "This file does not exist on remote server");
            
        }else{
            model = new ModelAndView("showfile");
            String filename = fileContent.get(0);
            fileContent.remove(0);
            model.addObject("filename", filename);
            model.addObject("filecontents", fileContent.get(0));
        }
        return model;
        
        


        
    }

  /*  @RequestMapping(value = "/getfile", method = RequestMethod.POST)
    @ResponseBody
    public String getFile(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        JSONObject filedata = new JSONObject();
        String fileId = request.getParameter("fileid");
        List<String> fileContent ;
        int intfileId = 0;
        if(fileId != null){
            intfileId = Integer.parseInt(fileId);
        }
        fileContent = filedelegate.getFile(intfileId);
        if(fileContent == null){
            redirectAttributes.addFlashAttribute("message", "This file does not exist on remote server");
            return "redirect:/error";
        }else{
            
            String filename = fileContent.get(0);
            fileContent.remove(0);
           
            filedata.put("filename", filename);
            filedata.put("filecontent", fileContent.get(0));
            
        }
        return filedata.toString();
        
        


        
    }*/


    @RequestMapping(value = "/savefile", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public  String saveFile(@RequestBody String fileJson) {
        String returnText = null;
        try {

            FileContent fileContent = new ObjectMapper().readValue(fileJson, FileContent.class);

            String name = fileContent.getName();
            String content = fileContent.getContent();
            String[] serverIds = fileContent.getServerIds();
            String isRestart = fileContent.getIsRestart();
            filedelegate.saveFile(name, content, serverIds, isRestart);
            returnText = "{}";
            return returnText;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
        returnText = "fail";
        return returnText;
    }


    @RequestMapping(value = "/checkmodify", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public  String checkModified(@RequestBody String fileJson) {
        String returnText = null;
        try {

            FileContent fileContent = new ObjectMapper().readValue(fileJson, FileContent.class);

            String name = fileContent.getName();
            String content = fileContent.getContent();
            String[] serverIds = fileContent.getServerIds();
            boolean isModified = filedelegate.checkModified(name, content, serverIds);
            if(isModified){
                returnText = "modified";
                return returnText;
            }else{
                returnText = "{}";
                return returnText;
            }


        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
        returnText = "fail";
        return returnText;
    }

    @RequestMapping(value = "/addfile", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody public File addFile(@RequestBody String fileJson) throws   IOException {

        File file = new ObjectMapper().readValue(fileJson, File.class);

        try {

            if (filedelegate.addFile(file) != null) {
                return file;
            } else {
                return null;
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
        return null;

    }
    
    @RequestMapping(value = "/deletefile", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody public String deletefile(@RequestBody String fileJson) throws   IOException {

       JSONObject newObj = new JSONObject(fileJson);
       String fileId = newObj.getString("fileId");

        try {

            if (filedelegate.deletefile(fileId) != null) {
                return "{}";
            } else {
                return "Failure";
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
        return null;

    }


}
