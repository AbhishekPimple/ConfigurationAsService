package com.cas.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.cas.dao.ServerDao;
import com.cas.model.Server;
import com.mysql.jdbc.PreparedStatement;

public class ServerDaoImpl implements ServerDao{
	DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Server createServer(Server server){

		boolean isServerExists = false;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		int proj_id=0;
		proj_id=Integer.parseInt(server.getProjectId());
		if(server != null){
			try{
				String query = "Select count(1) from server where server_name = ? and project_id = ?";
				pstmt = (PreparedStatement) dataSource.getConnection().prepareStatement(query);
				pstmt.setString(1, server.getServerName());
				pstmt.setInt(2, proj_id);
				resultSet = pstmt.executeQuery();
				if (resultSet.next()){
					isServerExists =  (resultSet.getInt(1) > 0);
				}	
				if(!isServerExists){
					String insertTableSQL = "INSERT INTO server"
							+ "(server_name, server_desc, project_id, server_username, server_password, server_ipaddress, server_logfile_path, server_type, server_restart_cmd) VALUES"
							+ "(?,?,?,?,?,?,?,?,?)";
					PreparedStatement preparedStatement = (PreparedStatement) dataSource.getConnection().prepareStatement(insertTableSQL);
					preparedStatement.setString(1, server.getServerName());
					preparedStatement.setString(2, server.getServerDesc());
					preparedStatement.setInt(3,Integer.parseInt(server.getProjectId()));
					preparedStatement.setString(4, server.getUsername());
					preparedStatement.setString(5, server.getPassword());
					preparedStatement.setString(6, server.getHostIP());
					preparedStatement.setString(7, server.getLogFilePath());
					preparedStatement.setString(8, server.getServerType());
					preparedStatement.setString(9, server.getRestartCmd());

					preparedStatement .executeUpdate();
					return server;
				}
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

	public Server updateServer(Server server) {
		//boolean isServerExists = false;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		int proj_id=0;
		proj_id=Integer.parseInt(server.getProjectId());
		int serv_id=0;
		serv_id=Integer.parseInt(server.getServerId());
		if(server != null){
			try{

				String updateTableSQL = "UPDATE server set"
						+ " server_name = ?," +" server_desc = ?,"+ " server_username = ?," + " server_password = ?," + " server_ipaddress = ?," 
						+ " server_logfile_path = ?," + " server_type = ?," + " server_restart_cmd = ? "
						+ " where server_id = ?" ;
				System.out.println(updateTableSQL);
				PreparedStatement preparedStatement = (PreparedStatement) dataSource.getConnection().prepareStatement(updateTableSQL);
				preparedStatement.setString(1, server.getServerName());
				preparedStatement.setString(2, server.getServerDesc());
				preparedStatement.setString(3, server.getUsername());
				preparedStatement.setString(4, server.getPassword());
				preparedStatement.setString(5, server.getHostIP());
				preparedStatement.setString(6, server.getLogFilePath());
				preparedStatement.setString(7, server.getServerType());
				preparedStatement.setString(8, server.getRestartCmd());
				preparedStatement.setInt(9, serv_id);

				preparedStatement .executeUpdate();
				return server;
			}

			catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}
}


