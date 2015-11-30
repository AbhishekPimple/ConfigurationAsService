package com.cas.delegate;

import com.cas.model.Server;
import com.cas.service.ServerService;

public class ServerDelegate {
	
	ServerService serverService;
	
	public ServerService getServerService() {
		return serverService;
	}
	
	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}
   
	public Server createServer(Server server) {
		return serverService.createServer(server);
		
	}

	public Object updateServer(Server server) {
		return serverService.updateServer(server);
	}
}
