package com.cas.service;

import java.util.List;

import com.cas.model.File;

public interface FileService {
	
	public List<String> getFile(int fileId);
	public void saveFile(int fileId);
	public File addFile(File file);
	
}
