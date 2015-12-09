package com.cas.delegate;


import java.util.List;

import com.cas.model.File;
import com.cas.service.FileService;

public class FileDelegate {
    private FileService fileService;



    public FileService getFileService() {
        return fileService;
    }



    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }



    public List<String> getFile(int fileId){
        return fileService.getFile(fileId);

    }

    public boolean saveFile(String name, String content, String[] serverIds, String isRestart){
        return fileService.saveFile(name, content, serverIds, isRestart);
    }




    public boolean checkModified(String name, String content, String[] serverIds) {
        return fileService.checkModified(name, content, serverIds);
    }



    public File addFile(File file) {
        return fileService.addFile(file);
    }



    public String deletefile(String fileId) {
        return fileService.deletefile(fileId);
    }
}
