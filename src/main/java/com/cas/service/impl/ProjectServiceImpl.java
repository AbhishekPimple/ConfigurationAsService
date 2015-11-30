package com.cas.service.impl;

import com.cas.dao.ProjectDao;
import com.cas.model.Project;
import com.cas.service.ProjectService;

public class ProjectServiceImpl implements ProjectService{
    ProjectDao projectDao;

    public ProjectDao getProjectDao() {
        return projectDao;
    }

    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public Project createProject(Project project) {


        return projectDao.createProject(project);
    }

    public Project updateProject(Project project) {
        return projectDao.updateProject(project);
    }

    public Project deleteProject(Project project) {
        return projectDao.deleteProject(project);
    }

}
