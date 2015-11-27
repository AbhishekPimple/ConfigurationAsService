package com.cas.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.cas.dao.FileDao;
import com.cas.model.File;
import com.mysql.jdbc.PreparedStatement;

public class FileDaoImpl implements FileDao{
	DataSource dataSource;
	
    public DataSource getDataSource() {
	return dataSource;
    }
    
    public void setDataSource(DataSource dataSource) {
	this.dataSource = dataSource;
    }

	public File addFile(File file) {
		// TODO Auto-generated method stub
		boolean isFileExists = false;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		if(file != null){
			try{

				for(int i=0; i<file.getServerId().length; i++) {
					int serverId=Integer.parseInt(file.getServerId()[i]);

					String query = "Select count(1) from config where config_file_path = ? and server_id = ?";
					pstmt = (PreparedStatement) dataSource.getConnection().prepareStatement(query);
					pstmt.setString(1, file.getFilePath());
					pstmt.setInt(2, serverId);
					resultSet = pstmt.executeQuery();
					if (resultSet.next()){
						isFileExists =  (resultSet.getInt(1) > 0);
					}
					if(isFileExists){
						return null;
					}
					if(!isFileExists){
						String insertTableSQL = "INSERT INTO config"
								+ "(config_name, config_desc,config_file_path,server_id) VALUES"
								+ "(?,?,?,?)";
						PreparedStatement preparedStatement = (PreparedStatement) dataSource.getConnection().prepareStatement(insertTableSQL);
						preparedStatement.setString(1, file.getFileName());
						preparedStatement.setString(2, file.getFileDesc());
						preparedStatement.setString(3, file.getFilePath());
						preparedStatement.setInt(4, serverId);
						preparedStatement .executeUpdate();
						
					}
					
				}
				return file;
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				if(resultSet != null){
					try {
						resultSet.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}



		return null;
	}


}
