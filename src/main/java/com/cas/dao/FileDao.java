package com.cas.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

public interface FileDao {
	
	public Map<String, String> getFileData(int fileId) throws SQLException;
	
	public Map<String, String> getServerData(int fileId, int serverId);
	
	public Timestamp getRetrievedTimestamp(int fileId);
	
}
