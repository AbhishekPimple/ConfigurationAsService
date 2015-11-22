package com.cas.service;

import java.sql.SQLException;

public interface LoginService {
	
	public boolean isValidUser(String emailId, String password) throws SQLException;
	
}
