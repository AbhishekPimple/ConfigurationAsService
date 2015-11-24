package com.cas.dao;

import java.sql.SQLException;

import com.cas.model.User;

public interface LoginDao {
	
	public User isValidUser(String emailId, String password) throws SQLException;
}
