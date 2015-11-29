package com.cas.model;

import java.io.Serializable;

public class User implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String username;
    private String emailId;
    private String password;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailId() {
        return this.emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
