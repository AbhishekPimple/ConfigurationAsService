package com.cas.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.cas.dao.ProjectDao;
import com.cas.model.Project;

public class ProjectDaoImpl implements ProjectDao {

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(ProjectDaoImpl.class.getName());

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Project createProject(Project project) {

        boolean isProjectExists = false;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        if (project != null) {
            try {
                String query = "Select count(1) from project where project_name = ?";
                pstmt = dataSource.getConnection().prepareStatement(query);
                pstmt.setString(ONE, project.getProjectName());
                resultSet = pstmt.executeQuery();
                if (resultSet.next()) {
                    isProjectExists = resultSet.getInt(ONE) > ZERO;
                }
                if (!isProjectExists) {
                    String insertTableSQL = "INSERT INTO project" + "(project_name, project_desc, workbench_id) VALUES"
                            + "(?,?,?)";
                    PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(insertTableSQL);
                    preparedStatement.setString(ONE, project.getProjectName());
                    preparedStatement.setString(TWO, project.getProjectDesc());
                    preparedStatement.setInt(THREE, Integer.parseInt(project.getWorkbenchId()));
                    preparedStatement.executeUpdate();
                    return project;
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

	public Project updateProject(Project project) {
		//boolean isProjectExists = false;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		int proj_id=0;
		proj_id=Integer.parseInt(project.getProjectId());
		if(project != null){
			try{

				String updateTableSQL = "UPDATE project set"
						+ " project_name = ?," +" project_desc = ?"
						+ " where project_id = ?" ;
				System.out.println(updateTableSQL);
				PreparedStatement preparedStatement = (PreparedStatement) dataSource.getConnection().prepareStatement(updateTableSQL);
				preparedStatement.setString(1, project.getProjectName());
				preparedStatement.setString(2, project.getProjectDesc());
				preparedStatement.setInt(3, proj_id);
				System.out.println(preparedStatement);

				preparedStatement .executeUpdate();
				return project;
			}

			catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Project deleteProject(Project project) {
		//boolean isProjectExists = false;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		int proj_id=0;
		proj_id=Integer.parseInt(project.getProjectId());
		if(project != null){
			try{

				String deleteTableSQL = "delete from project where project_id= ?";

				System.out.println(deleteTableSQL);
				PreparedStatement preparedStatement = (PreparedStatement) dataSource.getConnection().prepareStatement(deleteTableSQL);

				preparedStatement.setInt(1, proj_id);
				System.out.println(preparedStatement);

				preparedStatement .executeUpdate();
				return project;
			}

			catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}

}
