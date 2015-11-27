package com.cas.service.impl;

import com.cas.dao.ServerDao;
import com.cas.model.Server;
import com.cas.service.ServerService;

public class ServerServiceImpl implements ServerService{
	ServerDao serverDao;
	
	public ServerDao getServerDao() {
		return serverDao;
	}
	
	public void setServerDao(ServerDao serverDao) {
		this.serverDao = serverDao;
	}
   
	public Server createServer(Server server) {
		// TODO Auto-generated method stub
		return serverDao.createServer(server);
	}

}
