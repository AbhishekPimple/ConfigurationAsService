package com.cas.service;

import java.util.List;

public interface FileService {
	
	public List<String> getFile(int fileId);
	public boolean saveFile(String name, String content, String serverId);
	public boolean checkModified(String name, String content, String serverId);
}
