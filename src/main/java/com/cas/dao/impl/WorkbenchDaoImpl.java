package com.cas.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.cas.dao.WorkbenchDao;
import com.cas.model.Workbench;
import com.mysql.jdbc.PreparedStatement;

public class WorkbenchDaoImpl implements WorkbenchDao{
	DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@SuppressWarnings("resource")
	public Workbench createWorkbench(Workbench workbench, String emailId) {
		 
		boolean isWorkbenchExists = false;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		int user_id=0;
		if(workbench != null){
			try{
				String query = "Select user_id from user where user_email_id = ?";
				pstmt = (PreparedStatement) dataSource.getConnection().prepareStatement(query);
				pstmt.setString(1, emailId);
				resultSet = pstmt.executeQuery();
				if (resultSet.next()){
					//isWorkbenchExists =  (resultSet.getInt(1) > 0);
					user_id=resultSet.getInt("user_id");
				}
				
				String query1 = "Select count(1) from workbench where workbench_name = ? and user_id = ?";
				pstmt = (PreparedStatement) dataSource.getConnection().prepareStatement(query1);
				pstmt.setString(1, workbench.getWorkbenchName());
				pstmt.setInt(2, user_id);
				resultSet = pstmt.executeQuery();
				if (resultSet.next()){
					isWorkbenchExists =  (resultSet.getInt(1) > 0);
				}	
				if(!isWorkbenchExists){
					String insertTableSQL = "INSERT INTO workbench"
							+ "(workbench_name, workbench_desc, user_id) VALUES"
							+ "(?,?,?)";
					PreparedStatement preparedStatement = (PreparedStatement) dataSource.getConnection().prepareStatement(insertTableSQL);
					preparedStatement.setString(1, workbench.getWorkbenchName());
					preparedStatement.setString(2, workbench.getWorkbenchDesc());
					//preparedStatement.setInt(3,Integer.parseInt(workbench.getWorkbenchId()));
					preparedStatement.setInt(3, user_id);
					preparedStatement .executeUpdate();
					return workbench;
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


}
