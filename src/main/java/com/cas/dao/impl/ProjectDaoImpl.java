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

        int projId = ZERO;
        projId = Integer.parseInt(project.getProjectId());
        if (project != null) {
            try {

                String updateTableSQL = "UPDATE project set" + " project_name = ?," + " project_desc = ?"
                        + " where project_id = ?";

                PreparedStatement preparedStatement = (PreparedStatement) dataSource.getConnection()
                        .prepareStatement(updateTableSQL);
                preparedStatement.setString(ONE, project.getProjectName());
                preparedStatement.setString(TWO, project.getProjectDesc());
                preparedStatement.setInt(THREE, projId);

                preparedStatement.executeUpdate();
                return project;
            }catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return null;
    }

    public Project deleteProject(Project project) {
		if (project != null) {
			int projectId = Integer.parseInt(project.getProjectId());
			if(deleteProjectQuery(projectId)==0)
				return null;

			return project;
		}
		// TODO Auto-generated method stub
		return null;
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
