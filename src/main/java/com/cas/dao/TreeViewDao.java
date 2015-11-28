package com.cas.dao;

import java.sql.SQLException;

import org.json.JSONObject;

public interface TreeViewDao {
	public JSONObject populate(String emailId) throws SQLException;
		
}
