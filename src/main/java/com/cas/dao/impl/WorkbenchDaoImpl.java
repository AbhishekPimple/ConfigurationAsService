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
		//boolean isWorkbenchExists = false;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		int work_id=0;
		work_id=Integer.parseInt(workbench.getWorkbenchId());
		if(workbench != null){
			try{

				String updateTableSQL = "UPDATE workbench set"
						+ " workbench_name = ?," +" workbench_desc = ?"
						+ " where workbench_id = ?" ;
				System.out.println(updateTableSQL);
				PreparedStatement preparedStatement = (PreparedStatement) dataSource.getConnection().prepareStatement(updateTableSQL);
				preparedStatement.setString(1, workbench.getWorkbenchName());
				preparedStatement.setString(2, workbench.getWorkbenchDesc());
				preparedStatement.setInt(3, work_id);
				System.out.println(preparedStatement);

				preparedStatement .executeUpdate();
				return workbench;
			}

			catch(SQLException e){
				e.printStackTrace();
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

}
