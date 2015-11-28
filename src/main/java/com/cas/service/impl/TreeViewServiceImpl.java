package com.cas.service.impl;

import java.sql.SQLException;

import org.json.JSONObject;

import com.cas.dao.RegisterDao;
import com.cas.dao.TreeViewDao;
import com.cas.service.TreeViewService;

public class TreeViewServiceImpl implements TreeViewService{

	TreeViewDao treeViewDao;

	
	public TreeViewDao getTreeViewDao() {
		return treeViewDao;
	}


	public void setTreeViewDao(TreeViewDao treeViewDao) {
		this.treeViewDao = treeViewDao;
	}


	public JSONObject populate(String emailId) throws SQLException {
		// TODO Auto-generated method stub
		return treeViewDao.populate(emailId);
	}
	
}
