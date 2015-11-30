package com.cas.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.cas.dao.ServerDao;
import com.cas.model.Server;
import com.mysql.jdbc.PreparedStatement;

public class ServerDaoImpl implements ServerDao {
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

            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {

                        LOGGER.log(Level.SEVERE, e.getMessage(), e);

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


