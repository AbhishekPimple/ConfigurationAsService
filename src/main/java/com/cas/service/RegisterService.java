package com.cas.service;

import java.sql.SQLException;

public interface RegisterService {

    public boolean register(String emailId, String username, String password) throws SQLException;

}
