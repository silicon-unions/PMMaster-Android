package com.codesignet.pmp.practices.data_access_layer;

public class ProcessGroupsObject {
    private String ProcessGroupsName;

    private int ProcessGroupsLevel;

    public ProcessGroupsObject(String ProcessGroupsName,int ProcessGroupsLevel){
        setProcessGroupsName(ProcessGroupsName);
        setProcessGroupsLevel(ProcessGroupsLevel);
    }

    public int getProcessGroupsLevel() {
        return ProcessGroupsLevel;
    }

    public void setProcessGroupsLevel(int processGroupsLevel) {
        ProcessGroupsLevel = processGroupsLevel;
    }

    public String getProcessGroupsName() {
        return ProcessGroupsName;
    }

    public void setProcessGroupsName(String processGroupsName) {
        ProcessGroupsName = processGroupsName;
    }
}
