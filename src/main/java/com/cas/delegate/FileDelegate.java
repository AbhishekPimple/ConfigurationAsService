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

    public boolean saveFile(String name, String content, String serverId, String isRestart){
        return fileService.saveFile(name, content, serverId, isRestart);
    }




    public boolean checkModified(String name, String content, String serverId) {
        return fileService.checkModified(name, content, serverId);
    }



    public File addFile(File file) {
        return fileService.addFile(file);
    }



	public File deleteFile(File file) {
		return fileService.deleteFile(file);
	}
}
