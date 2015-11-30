package com.cas.model;

import java.io.Serializable;

public class Workbench implements Serializable{
	
	private static final long serialVersionUID = 1L;
	String workbenchName;
	String workbenchDesc;
	String workbenchId;
	public String getWorkbenchId() {
		return workbenchId;
	}
	public void setWorkbenchId(String workbenchId) {
		this.workbenchId = workbenchId;
	}
	public String getWorkbenchDesc() {
		return workbenchDesc;
	}
	
	public void setWorkbenchDesc(String workbenchDesc) {
		this.workbenchDesc = workbenchDesc;
	}
	
	public String getWorkbenchName() {
		return workbenchName;
	}
	
	public void setWorkbenchName(String workbenchName) {
		this.workbenchName = workbenchName;
	}

}
