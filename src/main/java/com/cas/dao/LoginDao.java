package com.cas.dao;

import java.sql.SQLException;

public interface LoginDao {
	
	public boolean isValidUser(String emailId, String password) throws SQLException;
}
