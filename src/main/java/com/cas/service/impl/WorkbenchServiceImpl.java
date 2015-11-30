package com.cas.service.impl;

import com.cas.dao.WorkbenchDao;
import com.cas.model.Workbench;
import com.cas.service.WorkbenchService;

public class WorkbenchServiceImpl implements WorkbenchService{
    WorkbenchDao workbenchDao;

    public WorkbenchDao getWorkbenchDao() {
        return workbenchDao;
    }

    public void setWorkbenchDao(WorkbenchDao workbenchDao) {
        this.workbenchDao = workbenchDao;
    }
    @Override
    public Workbench createWorkbench(Workbench workbench, String emailId) {

        return workbenchDao.createWorkbench(workbench, emailId);
    }

	public Workbench updateWorkbench(Workbench workbench) {
		return workbenchDao.updateWorkbench(workbench);
	}

}
