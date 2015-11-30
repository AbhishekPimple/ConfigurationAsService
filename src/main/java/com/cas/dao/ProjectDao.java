package com.cas.dao;

import com.cas.model.Project;

public interface ProjectDao {

	public Project createProject(Project project);

	public Project updateProject(Project project);

	public Project deleteProject(Project project);

}
