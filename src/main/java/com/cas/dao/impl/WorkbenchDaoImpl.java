package com.cas.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.cas.dao.WorkbenchDao;
import com.cas.model.Workbench;
import com.mysql.jdbc.PreparedStatement;

public class WorkbenchDaoImpl implements WorkbenchDao {
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(WorkbenchDaoImpl.class.getName());

    public Workbench updateWorkbench(Workbench workbench) {

        int workId = ZERO;
        workId = Integer.parseInt(workbench.getWorkbenchId());
        if (workbench != null) {
            try {

                String updateTableSQL = "UPDATE workbench set" + " workbench_name = ?," + " workbench_desc = ?"
                        + " where workbench_id = ?";

                PreparedStatement preparedStatement = (PreparedStatement) dataSource.getConnection()
                        .prepareStatement(updateTableSQL);
                preparedStatement.setString(ONE, workbench.getWorkbenchName());
                preparedStatement.setString(TWO, workbench.getWorkbenchDesc());
                preparedStatement.setInt(THREE, workId);

                preparedStatement.executeUpdate();
                return workbench;
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return null;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    @SuppressWarnings("resource")
    public Workbench createWorkbench(Workbench workbench, String emailId) {

        boolean isWorkbenchExists = false;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int userId = 0;
        if (workbench != null) {
            try {
                String query = "Select user_id from user where user_email_id = ?";
                pstmt = (PreparedStatement) dataSource.getConnection().prepareStatement(query);
                pstmt.setString(ONE, emailId);
                resultSet = pstmt.executeQuery();
                if (resultSet.next()) {

                    userId = resultSet.getInt("user_id");
                }

                String query1 = "Select count(1) from workbench where workbench_name = ? and user_id = ?";
                pstmt = (PreparedStatement) dataSource.getConnection().prepareStatement(query1);
                pstmt.setString(ONE, workbench.getWorkbenchName());
                pstmt.setInt(TWO, userId);
                resultSet = pstmt.executeQuery();
                if (resultSet.next()) {
                    isWorkbenchExists = resultSet.getInt(ONE) > ZERO;
                }
                if (!isWorkbenchExists) {
                    String insertTableSQL = "INSERT INTO workbench" + "(workbench_name, workbench_desc, user_id) VALUES"
                            + "(?,?,?)";
                    PreparedStatement preparedStatement = (PreparedStatement) dataSource.getConnection()
                            .prepareStatement(insertTableSQL);
                    preparedStatement.setString(ONE, workbench.getWorkbenchName());
                    preparedStatement.setString(TWO, workbench.getWorkbenchDesc());
                    preparedStatement.setInt(THREE, userId);
                    preparedStatement.executeUpdate();
                    return workbench;
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }

        }

        return null;
    }

	@Override
	public Workbench deleteWorkbench(Workbench workbench) {
		if (workbench != null) {
			int workbenchId = Integer.parseInt(workbench.getWorkbenchId());
			if(deleteWorkbenchQuery(workbenchId)==0)
				return null;

			return workbench;
		}
		// TODO Auto-generated method stub
		return null;
	}

	   private int deleteWorkbenchQuery(int workbenchId) {
		   try {
				String query = "Select project_id from project where workbench_id = ?";
				PreparedStatement pstmt = (PreparedStatement) dataSource.getConnection().prepareStatement(query);
				pstmt.setInt(ONE, workbenchId);
				System.out.println(pstmt);
				ResultSet resultSet = pstmt.executeQuery();
				
				while(resultSet.next()) {	
					if(deleteProjectQuery(resultSet.getInt(ONE)) == 0)
						return 0;
					
				}
				String query1 = "DELETE from workbench where workbench_id=?";
				try {
					PreparedStatement pstmt1 = (PreparedStatement) dataSource.getConnection().prepareStatement(query1);
					pstmt1.setInt(ONE, workbenchId);
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

	private int deleteProjectQuery(int projectId) {
	    	try {
				String query = "Select server_id from server where project_id = ?";
				PreparedStatement pstmt = (PreparedStatement) dataSource.getConnection().prepareStatement(query);
				pstmt.setInt(ONE, projectId);
				System.out.println(pstmt);
				ResultSet resultSet = pstmt.executeQuery();
				
				while(resultSet.next()) {	
					if(deleteServerQuery(resultSet.getInt(ONE)) == 0)
						return 0;
					
				}
				String query1 = "DELETE from project where project_id=?";
				try {
					PreparedStatement pstmt1 = (PreparedStatement) dataSource.getConnection().prepareStatement(query1);
					pstmt1.setInt(ONE, projectId);
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
