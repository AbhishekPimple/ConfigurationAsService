package com.cas.dao;

import com.cas.model.Workbench;

public interface WorkbenchDao {

    public Workbench createWorkbench(Workbench workbench, String emailId);

    public Workbench updateWorkbench(Workbench workbench);

}
