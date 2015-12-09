package com.cas.delegate;

import com.cas.model.Workbench;
import com.cas.service.WorkbenchService;

public class WorkbenchDelegate {

    WorkbenchService workbenchService;

    public WorkbenchService getWorkbenchService() {
        return workbenchService;
    }

    public void setWorkbenchService(WorkbenchService workbenchService) {
        this.workbenchService = workbenchService;
    }

    public Workbench createWorkbench(Workbench workbench, String emailId) {
        return workbenchService.createWorkbench(workbench, emailId);
    }

    public Workbench updateWorkbench(Workbench workbench) {
        return workbenchService.updateWorkbench(workbench);
    }

	public Workbench deleteWorkbench(Workbench workbench) {
		return workbenchService.deleteWorkbench(workbench);
	}

}
