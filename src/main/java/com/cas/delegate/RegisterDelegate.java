package com.cas.delegate;

import java.sql.SQLException;

import com.cas.service.RegisterService;;

public class RegisterDelegate {
    private RegisterService registerService;

    public RegisterService getRegisterService() {
        return this.registerService;
    }

    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    public boolean register(String emailId, String username, String password) throws SQLException {
        return registerService.register(emailId, username, password);
    }

}