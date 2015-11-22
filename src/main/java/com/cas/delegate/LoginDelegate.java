package com.cas.delegate;

import java.sql.SQLException;

import com.cas.service.LoginService;

public class LoginDelegate
{
	private LoginService loginService;

	public LoginService getLoginService()
	{
		return this.loginService;
	}

	public void setLoginService(LoginService loginService)
	{
		this.loginService = loginService;
	}

	public boolean isValidUser(String emailId, String password) throws SQLException
	{
	    return loginService.isValidUser(emailId, password);
	}
}