package com.cas.service.impl;

import java.sql.SQLException;

import com.cas.dao.RegisterDao;
import com.cas.service.RegisterService;

public class RegisterServiceImpl implements RegisterService {
	RegisterDao registerDao;
	
	public RegisterDao getRegisterDao() {
		return registerDao;
	}

	public void setRegisterDao(RegisterDao registerDao) {
		this.registerDao = registerDao;
	}

	public boolean register(String emailId, String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return registerDao.register(emailId, username, password);
	}

}
