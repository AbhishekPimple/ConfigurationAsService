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

    @Override
    public Server createServer(Server server) {

        return serverDao.createServer(server);
    }

    public Server updateServer(Server server) {
        return serverDao.updateServer(server);
    }

	@Override
	public Server deleteServer(Server server) {
		return serverDao.deleteServer(server);
	}

}
