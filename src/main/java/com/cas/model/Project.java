package com.cas.model;

import java.io.Serializable;

public class Project implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String projectName;
	String projectDesc;
	String workbenchId;
	
	
	public Project(){
		
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectDesc() {
		return projectDesc;
	}
	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}
	public String getWorkbenchId() {
		return workbenchId;
	}
	public void setWorkbenchId(String workbenchId) {
		this.workbenchId = workbenchId;
	}
	
}
