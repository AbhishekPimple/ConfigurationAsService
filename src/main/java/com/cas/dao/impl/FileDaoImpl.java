package com.cas.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.cas.dao.FileDao;

public class FileDaoImpl implements FileDao{
	DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Map<String, String> getFileData(int fileId) throws SQLException {
		/*   sh launchExpect.sh parag ubuntu.local root pull /home/parag/abc.txt /home/prasad/CAS/   */
		Map<String, String> fileData = new HashMap<String, String>();
		String query = "Select * from config where config_id=?";
		PreparedStatement pstmt =  dataSource.getConnection().prepareStatement(query);
		pstmt.setInt(1, fileId);
		ResultSet resultSet = pstmt.executeQuery();
		if (resultSet.next()){
			
			fileData.put("configfilepath", resultSet.getString("config_file_path"));
			fileData.put("serverid", resultSet.getString("server_id"));
		}
		
		String file_path = fileData.get("configfilepath");
		String file_name = file_path.substring(file_path.lastIndexOf("/")+1, file_path.length());
		fileData.put("filename", file_name);
		
		int serverId = Integer.parseInt(fileData.get("serverid"));
		
		
		String query1 = "Select * from server where server_id=?";
		PreparedStatement pstmt1 =  dataSource.getConnection().prepareStatement(query1);
		pstmt1.setInt(1, serverId);
		ResultSet resultSet1 = pstmt1.executeQuery();
		if (resultSet1.next()){
			fileData.put("username", resultSet1.getString("server_username"));
			fileData.put("hostname", resultSet1.getString("server_ipaddress"));
			fileData.put("password", resultSet1.getString("server_password"));
		}
		
		java.util.Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		

		String insertTableSQL = "INSERT INTO fileoperations"
				+ "(config_id, retrieved_at) VALUES"
				+ "(?,?) ON DUPLICATE KEY UPDATE retrieved_at=values(retrieved_at)";
		PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(insertTableSQL);
		preparedStatement.setInt(1, fileId);
		preparedStatement.setTimestamp(2, timestamp);;
		preparedStatement .executeUpdate();
		
		
		return fileData;
	}

	public Map<String, String> getServerData(int fileId, int serverId){
		// TODO Auto-generated method stub
		
		Map<String, String> serverData = new HashMap<String, String>();
		
		try{
			String query = "Select * from config where config_id=?";
			PreparedStatement pstmt =  dataSource.getConnection().prepareStatement(query);
			pstmt.setInt(1, fileId);
			ResultSet resultSet = pstmt.executeQuery();
			if (resultSet.next()){
				serverData.put("remotefilepath", resultSet.getString("config_file_path"));
			}	
			
			String query1 = "Select * from server where server_id=?";
			PreparedStatement pstmt1 =  dataSource.getConnection().prepareStatement(query1);
			pstmt1.setInt(1, serverId);
			ResultSet resultSet1 = pstmt1.executeQuery();
			if (resultSet1.next()){
				serverData.put("username", resultSet1.getString("server_username"));
				serverData.put("hostname", resultSet1.getString("server_ipaddress"));
				serverData.put("password", resultSet1.getString("server_password"));
			}	
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		
		
		return serverData;
	}

	public Timestamp getRetrievedTimestamp(int fileId) {
		Timestamp tStamp = null;
		try{
			String query = "Select * from fileoperations where config_id=?";
			PreparedStatement pstmt =  dataSource.getConnection().prepareStatement(query);
			pstmt.setInt(1, fileId);
			ResultSet resultSet = pstmt.executeQuery();
			if (resultSet.next()){
				tStamp = resultSet.getTimestamp("retrieved_at");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return tStamp;
	}

}
