package com.cas.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.cas.dao.RegisterDao;

public class RegisterDaoImpl implements RegisterDao {
	DataSource dataSource;

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public boolean register(String emailId, String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		boolean isUserExists = false;
		String query = "Select count(1) from user where user_email_Id = ?";
		PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);
		pstmt.setString(1, emailId);
		ResultSet resultSet = pstmt.executeQuery();
		if (resultSet.next()){
			isUserExists =  (resultSet.getInt(1) > 0);
		}	
		if(!isUserExists){
			String insertTableSQL = "INSERT INTO user"
					+ "(USER_EMAIL_ID, USER_NAME, USER_PASSWORD) VALUES"
					+ "(?,?,?)";
			PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(insertTableSQL);
			preparedStatement.setString(1, emailId);
			preparedStatement.setString(2, username);
			preparedStatement.setString(3, password);
			preparedStatement .executeUpdate();
		}
		return isUserExists;
		
		
	}

}
