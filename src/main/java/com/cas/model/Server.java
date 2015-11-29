package com.cas.model;

import java.io.Serializable;

public class Server implements Serializable{

    private static final long serialVersionUID = 1L;
    String serverName;
    String serverDesc;
    String hostIP;
    String username;
    String password;
    String serverType;
    String logFilePath;
    String restartCmd;
    String projectId;

    public String getHostIP() {
        return hostIP;
    }
    public String getLogFilePath() {
        return logFilePath;
    }
    public String getPassword() {
        return password;
    }
    public String getProjectId() {
        return projectId;
    }
    public String getRestartCmd() {
        return restartCmd;
    }
    public String getServerDesc() {
        return serverDesc;
    }
    public String getServerName() {
        return serverName;
    }
    public String getServerType() {
        return serverType;
    }
    public String getUsername() {
        return username;
    }
    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }
    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    public void setRestartCmd(String restartCmd) {
        this.restartCmd = restartCmd;
    }
    public void setServerDesc(String serverDesc) {
        this.serverDesc = serverDesc;
    }
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    public void setServerType(String serverType) {
        this.serverType = serverType;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
