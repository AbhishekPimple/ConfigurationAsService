package com.cas.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import com.cas.dao.FileDao;
import com.cas.model.User;

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
		PreparedStatement pstmt1 =  dataSource.getConnection().prepareStatement(query);
		pstmt.setInt(1, serverId);
		ResultSet resultSet1 = pstmt.executeQuery();
		if (resultSet1.next()){
			fileData.put("username", resultSet.getString("server_username"));
			fileData.put("hostname", resultSet.getString("server_ipaddress"));
			fileData.put("password", resultSet.getString("server_password"));
		}
		
		
		return fileData;
	}

	public Map<String, String> getServerData(int fileId){
		// TODO Auto-generated method stub
		
		/*Map<String, String> serverData = new HashMap<String, String>();
		String query = "Select * from config where config_id=?";
		
		   sh launchExpect.sh parag ubuntu.local root pull /home/parag/abc.txt /home/prasad/CAS/   
		
		try{
			String query1 = "Select config_file_path from config where =?";
			PreparedStatement pstmt =  dataSource.getConnection().prepareStatement(query);
			pstmt.setInt(1, fileId);
			ResultSet resultSet = pstmt.executeQuery();
			if (resultSet.next()){
				String file_path = resultSet.getString("config_file_path");
				String file_name = file_path.substring(file_path.lastIndexOf("/")+1, file_path.length());
				return file_name;
			}	
			
			
			PreparedStatement pstmt =  dataSource.getConnection().prepareStatement(query);
			pstmt.setInt(1, serverId);
			ResultSet resultSet = pstmt.executeQuery();
			if (resultSet.next()){
				serverData.put("username", resultSet.getString("server_username"));
				serverData.put("hostname", resultSet.getString("server_ipaddress"));
				serverData.put("password", resultSet.getString("server_password"));
				serverData.put("remotefilepath", resultSet.getString(""));
				serverData.put("username", resultSet.getString("server_username"));
			}	
		}catch(SQLException e){
			e.printStackTrace();
		}
		*/
		
		
		return null;
	}

}
