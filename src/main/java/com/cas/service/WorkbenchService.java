package com.cas.service;

import com.cas.model.Workbench;

public interface WorkbenchService {


    public Workbench createWorkbench(Workbench workbench, String emailId);

    public Workbench updateWorkbench(Workbench workbench);

	public Workbench deleteWorkbench(Workbench workbench);

}
