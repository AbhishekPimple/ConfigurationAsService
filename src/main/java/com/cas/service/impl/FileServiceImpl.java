package com.cas.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;

import com.cas.dao.FileDao;
import com.cas.service.FileService;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class FileServiceImpl implements FileService {

    static FileDao fileDao;
    private static final Logger LOGGER = Logger.getLogger(FileServiceImpl.class.getName());
    public static final int START = 8;
    public static final int END = 27;
    public static final int BEGINING = 0;
    public static final String SCRIPT_HOME = "SCRIPT_HOME";
    public static final String PASSWD = "password";
    public static final String HOSTNAME = "hostname";
    public static final String USERNAME = "username";
    public static final String SCRIPT_FILE = "launchExpect.sh";
    public static final String FILE_NOT_EXISTS_ERROR = "ERROR:File does not exist.";

    public FileDao getFileDao() {
        return fileDao;
    }

    public void setFileDao(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    public static boolean utilityReadInputStream(BufferedReader br, int fileId) {
        boolean isFileExists = true;
        try {
            
           
            while (br.ready()) {
                String readLine = br.readLine();
                if(FILE_NOT_EXISTS_ERROR.equals(readLine)){
                    isFileExists = false;
                    return isFileExists;
                }
                if (readLine.startsWith("Modify: ")) {
                   insertFileStamp(readLine.substring(START, END), fileId);
                }
         
                System.out.println(readLine);
                //LOGGER.log(Level.ALL, readLine);
            }
            br.close();
            
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return isFileExists;
    }

    public static boolean utilityCheckIfModified(BufferedReader br, int fileId) {
        boolean isModified = false;
        try {
            FileServiceImpl fileServiceImpl = new FileServiceImpl();
            while (br.ready()) {
                String readLine = br.readLine();
                if (readLine.startsWith("Modify: ")) {
                    isModified = fileServiceImpl.checkifModified(readLine.substring(START, END), fileId);
                }
                LOGGER.log(Level.ALL, readLine);
            }
            br.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return isModified;
    }

    @Override
    public List<String> getFile(int fileId) {

        List<String> fileContent = new ArrayList<String>();
        Map<String, String> fileData = new HashMap<String, String>();
        String propertyHome = System.getenv("PROPERTY_HOME");
        String scriptHome = System.getenv(SCRIPT_HOME);
        Scanner s = null;
        String baseFileName = null;
        try {
            fileData = fileDao.getFileData(fileId);
            baseFileName = fileId + "_" + fileData.get("filename");

            String cmd = "sh " + scriptHome + SCRIPT_FILE + " " + scriptHome + " " + fileData.get(USERNAME) + " "
                    + fileData.get(HOSTNAME) + " " + fileData.get(PASSWD) + " pull" + " "
                    + fileData.get("configfilepath") + " " + propertyHome;

            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();

            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader r2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            boolean isFileExists = false;
            isFileExists = utilityReadInputStream(r, fileId);
            
            if(!isFileExists){
                return null;
            }else{
                utilityReadInputStream(r2, 0);

                File oldFile = new File(propertyHome + fileData.get("filename"));
                File newFile = new File(propertyHome + baseFileName);

                oldFile.renameTo(newFile);

                fileContent.add(baseFileName);

                s = new Scanner(newFile);
               
                String data = Files.toString(newFile, Charsets.UTF_8);
                String refineddata = StringEscapeUtils.escapeJavaScript(data);
                System.out.println(refineddata);
                fileContent.add(refineddata);
                /*while (s.hasNextLine()) {
                    fileContent.add(s.nextLine());
                }*/
            }
            
            

        } catch (SQLException e1) {
            LOGGER.log(Level.SEVERE, e1.getMessage(), e1);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (s != null) {
                s.close();
            }
        }
        return fileContent;

    }

    @Override
    public boolean saveFile(String name, String content, String serverId, String isRestart) {

        String newContent = content;
        String propertyHome = System.getenv("PROPERTY_HOME");
        String scriptHome = System.getenv(SCRIPT_HOME);
        Map<String, String> replaceMap = new HashMap<String, String>();
        replaceMap.put("<br><br>", "\n");
        replaceMap.put("<div>", "");
        replaceMap.put("</div>", "");
        replaceMap.put("<br>", "\n");

        for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
            newContent = newContent.replaceAll(entry.getKey(), entry.getValue());
        }

        try {
            String filename = propertyHome + name;
            int splitIndex = name.indexOf("_");
            int fileId = Integer.parseInt(name.substring(BEGINING, splitIndex));
            String oldFileName = name.substring(splitIndex + 1, name.length());
            File thisFile = new File(filename);
            File oldFile = new File(propertyHome + oldFileName);
            thisFile.renameTo(oldFile);
            writeDataToFile(oldFile, newContent);

            Map<String, String> serverData = fileDao.getServerData(fileId, Integer.parseInt(serverId));
            String remotePath = serverData.get("remotefilepath");
            remotePath = remotePath.substring(BEGINING, remotePath.lastIndexOf("/") + 1);
            String cmd = "sh " + scriptHome + SCRIPT_FILE + " " + scriptHome + " " + serverData.get(USERNAME) + " "
                    + serverData.get(HOSTNAME) + " " + serverData.get(PASSWD) + " push" + " " + propertyHome
                    + oldFileName + " " + remotePath;

            Process p = Runtime.getRuntime().exec(cmd);

            p.waitFor();

            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader r2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            utilityReadInputStream(r, 0);
            utilityReadInputStream(r2, 0);
            if ("true".equals(isRestart)) {
                performRestartFunctionality(scriptHome, serverData);
            }
            return false;

        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);

        } catch (IOException e) {

            LOGGER.log(Level.SEVERE, e.getMessage(), e);

        } catch (InterruptedException e) {

            LOGGER.log(Level.SEVERE, e.toString());

        }

        return false;

    }
    
    
    public void performRestartFunctionality(String scriptHome, Map<String, String> serverData){
        String restartCommand = "sh " + scriptHome + SCRIPT_FILE + " " + scriptHome + " "
                + serverData.get(USERNAME) + " " + serverData.get(HOSTNAME) + " " + serverData.get(PASSWD)
                + " restart ";

        Process processRestart = null;
        try {
            processRestart = new ProcessBuilder(restartCommand, serverData.get("restartcommand")).start();
            processRestart.waitFor();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
        

        BufferedReader iStream = new BufferedReader(new InputStreamReader(processRestart.getInputStream()));
        BufferedReader eStream = new BufferedReader(new InputStreamReader(processRestart.getErrorStream()));
        utilityReadInputStream(iStream, 0);
        utilityReadInputStream(eStream, 0);
    }
    
    public void writeDataToFile(File oldFile, String content) {
        try {

            FileWriter fileWriter = new FileWriter(oldFile, false);
            fileWriter.write(content);
            fileWriter.close();

        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);

        }
    }
    

    public static void insertFileStamp(String timestampString, int fileId) {
        try {
            Timestamp filetimestamp;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(timestampString);
            filetimestamp = new java.sql.Timestamp(parsedDate.getTime());
            fileDao.insertFileTimeStamp(filetimestamp, fileId);

        } catch (java.text.ParseException e) {

            LOGGER.log(Level.SEVERE, e.toString());
        }
    }

    private boolean checkifModified(String substring, int fileId) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(substring);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            Timestamp firstTimeStamp = fileDao.getRetrievedTimestamp(fileId);

            if (timestamp.after(firstTimeStamp)) {
                return true;
            }

        } catch (java.text.ParseException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
        return false;
    }

    @Override
    public boolean checkModified(String name, String content, String serverId) {

        String scriptHome = System.getenv(SCRIPT_HOME);
        int splitIndex = name.indexOf("_");
        int fileId = Integer.parseInt(name.substring(BEGINING, splitIndex));

        boolean isModified = false;
        Map<String, String> serverData = fileDao.getServerData(fileId, Integer.parseInt(serverId));

        try {

            String remotePathCheck = serverData.get("remotefilepath");

            String checkCommand = "sh " + scriptHome + SCRIPT_FILE + " " + scriptHome + " " + serverData.get(USERNAME)
                    + " " + serverData.get(HOSTNAME) + " " + serverData.get(PASSWD) + " getModTime" + " "
                    + remotePathCheck;

            Process checkProc = Runtime.getRuntime().exec(checkCommand);
            checkProc.waitFor();

            BufferedReader iStream = new BufferedReader(new InputStreamReader(checkProc.getInputStream()));
            BufferedReader eStream = new BufferedReader(new InputStreamReader(checkProc.getErrorStream()));
            isModified = utilityCheckIfModified(iStream, fileId);
            utilityReadInputStream(eStream, 0);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);

        }

        return isModified;
    }

    @Override
    public com.cas.model.File addFile(com.cas.model.File file) {

        return fileDao.addFile(file);
    }

}
