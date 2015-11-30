package com.cas.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.cas.dao.LoginDao;
import com.cas.model.User;

public class LoginDaoImpl implements LoginDao {

    private static final int ONE = 1;
    private static final int TWO = 2;

    DataSource dataSource;

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User isValidUser(String emailId, String password) throws SQLException {
        String query = "Select * from user where user_email_id = ? and user_password = ?";
        User user = null;
        PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);
        pstmt.setString(ONE, emailId);
        pstmt.setString(TWO, password);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            user = new User();
            user.setEmailId(resultSet.getString("user_email_id"));
            user.setUsername(resultSet.getString("user_name"));
            return user;
        }

        return user;

    }

}