package com.cas.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.cas.dao.FileDao;
import com.cas.model.File;

public class FileDaoImpl implements FileDao {
    DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(FileDaoImpl.class.getName());

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
 
    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public File addFile(File file) {

        boolean isFileExists = false;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        if (file != null) {
            try {

                for (int i = ZERO; i < file.getServerId().length; i++) {
                    int serverId = Integer.parseInt(file.getServerId()[i]);

                    String query = "Select count(1) from config where config_name = ? and server_id = ? ";
                    pstmt = dataSource.getConnection().prepareStatement(query);
                    pstmt.setString(ONE, file.getFileName());
                    pstmt.setInt(TWO, serverId);
                    //pstmt.setString(THREE, file.getFileName());
                    resultSet = pstmt.executeQuery();
                    if (resultSet.next()) {
                        isFileExists = resultSet.getInt(ONE) > ZERO;
                    }
                    if (isFileExists) {
                        return null;
                    }
                    if (!(resultSet.getInt(ONE) > ZERO)) {
                        String remotePath = file.getFilePath();
                        String actualFileName = remotePath.substring(remotePath.lastIndexOf("/")+1, remotePath.length());
                        
                        String insertTableSQL = "INSERT INTO config"
                                + "(config_name ,config_desc,config_file_path,server_id,actual_file_name) VALUES" + "(?,?,?,?,?)";
                        PreparedStatement preparedStatement = dataSource.getConnection()
                                .prepareStatement(insertTableSQL);
                        preparedStatement.setString(ONE, file.getFileName());
                        preparedStatement.setString(TWO, file.getFileDesc());
                        preparedStatement.setString(THREE, file.getFilePath());
                        preparedStatement.setInt(FOUR, serverId);
                        preparedStatement.setString(FIVE, actualFileName);
                        preparedStatement.executeUpdate();

                    }

                }
                return file;
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

    @Override
    public Map<String, String> getFileData(int fileId) throws SQLException {

        Map<String, String> fileData = new HashMap<String, String>();
        String query = "Select * from config where config_id=?";
        PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);
        pstmt.setInt(ONE, fileId);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {

            fileData.put("configfilepath", resultSet.getString("config_file_path"));
            fileData.put("serverid", resultSet.getString("server_id"));
        }

        String filePath = fileData.get("configfilepath");
        String fileName = filePath.substring(filePath.lastIndexOf("/") + ONE, filePath.length());
        fileData.put("filename", fileName);

        int serverId = Integer.parseInt(fileData.get("serverid"));

        String query1 = "Select * from server where server_id=?";
        PreparedStatement pstmt1 = dataSource.getConnection().prepareStatement(query1);
        pstmt1.setInt(ONE, serverId);
        ResultSet resultSet1 = pstmt1.executeQuery();
        if (resultSet1.next()) {
            fileData.put("username", resultSet1.getString("server_username"));
            fileData.put("hostname", resultSet1.getString("server_ipaddress"));
            fileData.put("password", resultSet1.getString("server_password"));
        }

        return fileData;
    }

    @Override
    public Map<String, String> getServerData(int fileId, int serverId) {

        Map<String, String> serverData = new HashMap<String, String>();

        try {
            String query = "Select * from config where config_id=?";
            PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);
            pstmt.setInt(ONE, fileId);
            ResultSet resultSet = pstmt.executeQuery();
            String actualFileName=null;
            if (resultSet.next()) {
                actualFileName = resultSet.getString("config_name");
                //serverData.put("remotefilepath", resultSet.getString("actual_file_name"));
            }
            String query2 = "Select * from config where config_name=? and server_id=?";
            PreparedStatement pstmt2 = dataSource.getConnection().prepareStatement(query2);
            pstmt2.setString(ONE, actualFileName);
            pstmt2.setInt(TWO, serverId);
            ResultSet resultSet2 = pstmt2.executeQuery();
            
            if (resultSet2.next()) {
                
                serverData.put("remotefilepath", resultSet2.getString("config_file_path"));
            }

            String query1 = "Select * from server where server_id=?";
            PreparedStatement pstmt1 = dataSource.getConnection().prepareStatement(query1);
            pstmt1.setInt(ONE, serverId);
            ResultSet resultSet1 = pstmt1.executeQuery();
            if (resultSet1.next()) {
                serverData.put("username", resultSet1.getString("server_username"));
                serverData.put("hostname", resultSet1.getString("server_ipaddress"));
                serverData.put("password", resultSet1.getString("server_password"));
                String serverRestartCommand = "\"" + resultSet1.getString("server_restart_cmd") + "\"";
                serverData.put("restartcommand", serverRestartCommand);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return serverData;
    }


    @Override
    public Timestamp getRetrievedTimestamp(int fileId, String serverId) {
        Timestamp tStamp = null;
        try {
           String query1 = "Select config_name from config where config_id = ?";
           
           PreparedStatement pstmt1 = dataSource.getConnection().prepareStatement(query1);
           pstmt1.setInt(ONE, fileId);
            ResultSet resultSet1 = pstmt1.executeQuery();
            String config_name=null;
           if (resultSet1.next()) {
               config_name = resultSet1.getString("config_name");
           }
           
            
            String query = "Select * from fileoperations where config_name=? and server_id=?";
            PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);
            pstmt.setString(ONE, config_name);
            pstmt.setString(TWO, serverId);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                tStamp = resultSet.getTimestamp("retrieved_at");
            }
            else
            {
                return null;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return tStamp;
    }


    @Override
    public void insertFileTimeStamp(Timestamp timestamp, int fileId) {

        try {
            String query1 = "Select config_name, server_id from config where config_id = ?";
            
            PreparedStatement pstmt1 = dataSource.getConnection().prepareStatement(query1);
            pstmt1.setInt(ONE, fileId);
             ResultSet resultSet1 = pstmt1.executeQuery();
             String config_name=null;
             int serverId = 0;
            if (resultSet1.next()) {
                config_name = resultSet1.getString("config_name");
                serverId = resultSet1.getInt("server_id");
            }
            
            String insertTableSQL = "INSERT INTO fileoperations" + "(config_name, retrieved_at,server_id) VALUES"
                    + "(?,?,?) ON DUPLICATE KEY UPDATE retrieved_at=values(retrieved_at)";
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(insertTableSQL);
            
            preparedStatement.setString(ONE, config_name);
            preparedStatement.setTimestamp(TWO, timestamp);
            preparedStatement.setInt(THREE, serverId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    @Override
    public String deletefile(String fileId) {
        int intfileId = Integer.parseInt(fileId);
        String deleteSQL = "DELETE From config WHERE config_id = ?";

        try {
            
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(deleteSQL);
            preparedStatement.setInt(1, intfileId);

          
            preparedStatement.executeUpdate();

           
            
        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }
        
        return "";
    }

}
