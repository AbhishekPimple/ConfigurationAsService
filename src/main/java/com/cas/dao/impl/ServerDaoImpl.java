package com.cas.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.cas.dao.ServerDao;
import com.cas.model.Server;
import com.mysql.jdbc.PreparedStatement;

public class ServerDaoImpl implements ServerDao{
	DataSource dataSource;
	private static final Logger LOGGER = Logger.getLogger(ServerDaoImpl.class.getName());

	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final int FIVE = 5;
	private static final int SIX = 6;
	private static final int SEVEN = 7;
	private static final int EIGHT = 8;
	private static final int NINE = 9;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Server createServer(Server server) {

		boolean isServerExists = false;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		int projId;
		projId = Integer.parseInt(server.getProjectId());
		if (server != null) {
			try {
				String query = "Select count(1) from server where server_name = ? and project_id = ?";
				pstmt = (PreparedStatement) dataSource.getConnection().prepareStatement(query);
				pstmt.setString(ONE, server.getServerName());
				pstmt.setInt(TWO, projId);
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) {
					isServerExists = resultSet.getInt(ONE) > ZERO;
				}
				if (!isServerExists) {
					String insertTableSQL = "INSERT INTO server"
							+ "(server_name, server_desc, project_id, server_username, server_password, server_ipaddress, server_logfile_path, server_type, server_restart_cmd) VALUES"
							+ "(?,?,?,?,?,?,?,?,?)";
					PreparedStatement preparedStatement = (PreparedStatement) dataSource.getConnection()
							.prepareStatement(insertTableSQL);
					preparedStatement.setString(ONE, server.getServerName());
					preparedStatement.setString(TWO, server.getServerDesc());
					preparedStatement.setInt(THREE, Integer.parseInt(server.getProjectId()));
					preparedStatement.setString(FOUR, server.getUsername());
					preparedStatement.setString(FIVE, server.getPassword());
					preparedStatement.setString(SIX, server.getHostIP());
					preparedStatement.setString(SEVEN, server.getLogFilePath());
					preparedStatement.setString(EIGHT, server.getServerType());
					preparedStatement.setString(NINE, server.getRestartCmd());

					preparedStatement.executeUpdate();
					return server;
				}
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);

			}

		}

		return null;

	}

	public Server updateServer(Server server) {
		int projId = ZERO;
		projId = Integer.parseInt(server.getProjectId());
		int servId = ZERO;
		servId = Integer.parseInt(server.getServerId());
		if (server != null) {
			try {

				String updateTableSQL = "UPDATE server set" + " server_name = ?," + " server_desc = ?,"
						+ " server_username = ?," + " server_password = ?," + " server_ipaddress = ?,"
						+ " server_logfile_path = ?," + " server_type = ?," + " server_restart_cmd = ? "
						+ " where server_id = ?";
				PreparedStatement preparedStatement = (PreparedStatement) dataSource.getConnection()
						.prepareStatement(updateTableSQL);
				preparedStatement.setString(ONE, server.getServerName());
				preparedStatement.setString(TWO, server.getServerDesc());
				preparedStatement.setString(THREE, server.getUsername());
				preparedStatement.setString(FOUR, server.getPassword());
				preparedStatement.setString(FIVE, server.getHostIP());
				preparedStatement.setString(SIX, server.getLogFilePath());
				preparedStatement.setString(SEVEN, server.getServerType());
				preparedStatement.setString(EIGHT, server.getRestartCmd());
				preparedStatement.setInt(NINE, servId);

				preparedStatement.executeUpdate();
				return server;
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return null;
	}

	@Override
	public Server deleteServer(Server server) {
		if (server != null) {
			int serverId = Integer.parseInt(server.getServerId());
			if(deleteServerQuery(serverId)==0)
				return null;

			return server;
		}
		// TODO Auto-generated method stub
		return null;
	}

	public int deleteServerQuery(int serverId) {
		try {
			String query = "Select config_id from config where server_id = ?";
			PreparedStatement pstmt = (PreparedStatement) dataSource.getConnection().prepareStatement(query);
			pstmt.setInt(ONE, serverId);
			System.out.println(pstmt);
			ResultSet resultSet = pstmt.executeQuery();
			//FileDaoImpl fileDaoImpl = new FileDaoImpl();
			while(resultSet.next()) {	
				if(deleteConfig(resultSet.getInt(ONE)) == 0)
					return 0;
				//deleteConfig(resultSet.getInt(ONE));
			}
			String query1 = "DELETE from server where server_id=?";
			try {
				PreparedStatement pstmt1 = (PreparedStatement) dataSource.getConnection().prepareStatement(query1);
				pstmt1.setInt(ONE, serverId);
				System.out.println(pstmt1);
				if(pstmt1.executeUpdate()==0)
					return 0;
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);

		}
		return 1;

	}

	public int deleteConfig(int configId) {
		String query = "DELETE from config where config_id=?";
		try {
			PreparedStatement pstmt = (PreparedStatement) dataSource.getConnection().prepareStatement(query);
			pstmt.setInt(ONE, configId);
			System.out.println(pstmt);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return 0;
	}




}

