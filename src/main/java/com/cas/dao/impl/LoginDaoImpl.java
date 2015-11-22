package com.cas.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.cas.dao.LoginDao;

public class LoginDaoImpl implements LoginDao {

	DataSource dataSource;

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean isValidUser(String emailId, String password) throws SQLException {
		String query = "Select count(1) from user where user_email_id = ? and user_password = ?";
		PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);
		pstmt.setString(1, emailId);
		pstmt.setString(2, password);
		ResultSet resultSet = pstmt.executeQuery();
		if (resultSet.next())
			return (resultSet.getInt(1) > 0);
		else
			return false;
	}

}