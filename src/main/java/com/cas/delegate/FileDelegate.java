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
	
	public void saveFile(int fileId){
		fileService.saveFile(fileId);
	}



	public File addFile(File file) {
		return fileService.addFile(file);
	}
}
