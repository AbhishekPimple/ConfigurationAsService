package com.cas.service;

import com.cas.model.Server;

public interface ServerService {

    public Server createServer(Server server);

    public Server updateServer(Server server);

	public Server deleteServer(Server server);

}
