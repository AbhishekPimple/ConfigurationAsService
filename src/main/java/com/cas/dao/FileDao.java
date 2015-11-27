package com.cas.dao;

import java.sql.SQLException;
import java.util.Map;

public interface FileDao {
	
	public Map<String, String> getFileData(int fileId) throws SQLException;
	
	public Map<String, String> getServerData(int fileId);
	
}
