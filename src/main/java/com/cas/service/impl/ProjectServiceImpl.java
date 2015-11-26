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

	public Project createProject(Project project) {
		// TODO Auto-generated method stub
		
		return projectDao.createProject(project);
	}

}
