package com.cas.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.cas.dao.RegisterDao;

public class RegisterDaoImpl implements RegisterDao {
    DataSource dataSource;
    
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean register(String emailId, String username, String password) throws SQLException {

        boolean isUserExists = false;
        String query = "Select count(1) from user where user_email_Id = ?";
        PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);
        pstmt.setString(ONE, emailId);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            isUserExists = resultSet.getInt(ONE) > ZERO;
        }
        if (!isUserExists) {
            String insertTableSQL = "INSERT INTO user" + "(USER_EMAIL_ID, USER_NAME, USER_PASSWORD) VALUES" + "(?,?,?)";
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(insertTableSQL);
            preparedStatement.setString(ONE, emailId);
            preparedStatement.setString(TWO, username);
            preparedStatement.setString(THREE, password);
            preparedStatement.executeUpdate();
        }
        return isUserExists;

    }

}
