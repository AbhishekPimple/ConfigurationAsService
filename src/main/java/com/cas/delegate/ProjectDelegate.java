package com.cas.delegate;

import com.cas.model.Project;
import com.cas.service.ProjectService;

public class ProjectDelegate {
    ProjectService projectService;

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public Project createProject(Project project){
        return projectService.createProject(project);
    }

}
