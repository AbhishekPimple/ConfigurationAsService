package com.cas.dao;

import java.sql.SQLException;

public interface RegisterDao {
	public boolean register(String emailId, String username, String password) throws SQLException;
}
