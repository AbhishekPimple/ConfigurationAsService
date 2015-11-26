package com.cas.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.cas.dao.ProjectDao;
import com.cas.model.Project;

public class ProjectDaoImpl implements ProjectDao {
	DataSource dataSource;

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public Project createProject(Project project) {
		// TODO Auto-generated method stub
		boolean isProjectExists = false;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		if(project != null){
			try{
				String query = "Select count(1) from project where project_name = ?";
				pstmt = dataSource.getConnection().prepareStatement(query);
				pstmt.setString(1, project.getProjectName());
				resultSet = pstmt.executeQuery();
				if (resultSet.next()){
					isProjectExists =  (resultSet.getInt(1) > 0);
				}	
				if(!isProjectExists){
					String insertTableSQL = "INSERT INTO project"
							+ "(project_name, project_desc, workbench_id) VALUES"
							+ "(?,?,?)";
					PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(insertTableSQL);
					preparedStatement.setString(1, project.getProjectName());
					preparedStatement.setString(2, project.getProjectDesc());
					preparedStatement.setInt(3,Integer.parseInt(project.getWorkbenchId()));
					preparedStatement .executeUpdate();
					return project;
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

}
