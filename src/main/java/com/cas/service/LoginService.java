package com.cas.service;

import java.sql.SQLException;

import com.cas.model.User;

public interface LoginService {
	
	public User isValidUser(String emailId, String password) throws SQLException;
	
}
