package com.cas.model;

import java.io.Serializable;

public class FileContent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String content;
	String serverId;
	String isRestart;
	
	public FileContent(){
		
	}
	
	
	public String getIsRestart() {
		return isRestart;
	}

	public void setIsRestart(String isRestart) {
		this.isRestart = isRestart;
	}


	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public FileContent(String name, String content){
		this.name = name;
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
