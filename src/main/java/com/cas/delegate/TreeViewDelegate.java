package com.cas.delegate;

import java.sql.SQLException;

import org.json.JSONObject;

import com.cas.service.TreeViewService;

public class TreeViewDelegate {

	private TreeViewService treeViewService;

	

	public TreeViewService getTreeViewService() {
		return treeViewService;
	}



	public void setTreeViewService(TreeViewService treeViewService) {
		this.treeViewService = treeViewService;
	}



	public JSONObject populate(String emailId) throws SQLException
	{
		return treeViewService.populate(emailId);
	}
}
