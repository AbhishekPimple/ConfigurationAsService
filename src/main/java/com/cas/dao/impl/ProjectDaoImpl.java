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
                pstmt.setString(1, project.getProjectName());
                resultSet = pstmt.executeQuery();
                if (resultSet.next()) {
                    isProjectExists = resultSet.getInt(1) > 0;
                }
                if (!isProjectExists) {
                    String insertTableSQL = "INSERT INTO project" + "(project_name, project_desc, workbench_id) VALUES"
                            + "(?,?,?)";
                    PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(insertTableSQL);
                    preparedStatement.setString(1, project.getProjectName());
                    preparedStatement.setString(2, project.getProjectDesc());
                    preparedStatement.setInt(3, Integer.parseInt(project.getWorkbenchId()));
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

}
