package com.cas.dao;


import com.cas.model.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

public interface FileDao {

	public File addFile(File file); 

	public Map<String, String> getFileData(int fileId) throws SQLException;

	public Map<String, String> getServerData(int fileId, int serverId);

	public Timestamp getRetrievedTimestamp(int fileId, String serverId);

	public void insertFileTimeStamp(Timestamp timestamp, int fileId);


	public String deletefile(String fileId);

}
