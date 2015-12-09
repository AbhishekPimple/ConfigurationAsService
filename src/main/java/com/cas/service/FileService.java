package com.cas.service;

import java.util.List;

import com.cas.model.File;

public interface FileService {

	public List<String> getFile(int fileId);

	public File addFile(File file);

	public boolean saveFile(String name, String content, String[] serverIds, String isRestart);
	public boolean checkModified(String name, String content, String[] serverIds);

	public String deletefile(String fileId);

}
