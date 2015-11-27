package com.cas.model;

import java.io.Serializable;

public class File implements Serializable{
	private static final long serialVersionUID = 1L;
	String [] serverId;
	String fileName;
	String filePath;
	String fileDesc;
	
	public String getFileDesc() {
		return fileDesc;
	}
	public String getFileName() {
		return fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public String[] getServerId() {
		return serverId;
	}
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public void setServerId(String[] serverId) {
		this.serverId = serverId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
