package com.cas.service;

import com.cas.model.Project;
import com.cas.model.Workbench;

public interface WorkbenchService {

	public Workbench createWorkbench(Workbench workbench, String emailId);

	public Workbench updateWorkbench(Workbench workbench);
}
