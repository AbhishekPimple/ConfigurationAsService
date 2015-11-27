package com.cas.delegate;

import java.util.List;

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
	
	public boolean saveFile(String name, String content, String serverId){
		return fileService.saveFile(name, content, serverId);
	}
	



	public boolean checkModified(String name, String content, String serverId) {
		return fileService.checkModified(name, content, serverId);
	}
}
