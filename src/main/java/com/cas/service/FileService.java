package com.cas.service;

import java.util.List;

public interface FileService {
	
	public List<String> getFile(int fileId);
	public void saveFile(String name, String content, String serverId);
}
