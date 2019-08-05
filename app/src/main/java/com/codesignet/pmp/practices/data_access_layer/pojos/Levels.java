package com.codesignet.pmp.practices.data_access_layer.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Levels {
    @SerializedName("Initiating Process Group")
    
    private String initiatingProcessGroup;
    @SerializedName("Planning Process Group")
    private String planningProcessGroup;
    @SerializedName("Executing Process Group")
    private String executingProcessGroup;
    @SerializedName("Monitoring and Controlling Process Group")
    private String monitoringAndControllingProcessGroup;
    @SerializedName("Closing Process Group")

    private String closingProcessGroup;
    @SerializedName("Project integration management")
    private String projectIntegrationManagement;
    @SerializedName("Project scope management")
    private String projectScopeManagement;
    @SerializedName("Project schedule management")
    private String projectScheduleManagement;
    @SerializedName("Project cost management")
    private String projectCostManagement;
    @SerializedName("Project quality management")
    private String projectQualityManagement;
    @SerializedName("Project resource management")
    private String projectResourceManagement;
    @SerializedName("Project communications management")
    private String projectCommunicationsManagement;
    @SerializedName("Project risk management")
    private String projectRiskManagement;
    @SerializedName("Project procurement management")
    private String projectProcurementManagement;
    @SerializedName("Project stakeholder management")
    private String projectStakeholderManagement;

    public String getInitiatingProcessGroup() {
        return initiatingProcessGroup;
    }

    public void setInitiatingProcessGroup(String initiatingProcessGroup) {
        this.initiatingProcessGroup = initiatingProcessGroup;
    }

    public String getPlanningProcessGroup() {
        return planningProcessGroup;
    }

    public void setPlanningProcessGroup(String planningProcessGroup) {
        this.planningProcessGroup = planningProcessGroup;
    }

    public String getExecutingProcessGroup() {
        return executingProcessGroup;
    }

    public void setExecutingProcessGroup(String executingProcessGroup) {
        this.executingProcessGroup = executingProcessGroup;
    }

    public String getMonitoringAndControllingProcessGroup() {
        return monitoringAndControllingProcessGroup;
    }

    public void setMonitoringAndControllingProcessGroup(String monitoringAndControllingProcessGroup) {
        this.monitoringAndControllingProcessGroup = monitoringAndControllingProcessGroup;
    }

    public String getClosingProcessGroup() {
        return closingProcessGroup;
    }

    public void setClosingProcessGroup(String closingProcessGroup) {
        this.closingProcessGroup = closingProcessGroup;
    }

    public String getProjectIntegrationManagement() {
        return projectIntegrationManagement;
    }

    public void setProjectIntegrationManagement(String projectIntegrationManagement) {
        this.projectIntegrationManagement = projectIntegrationManagement;
    }

    public String getProjectScopeManagement() {
        return projectScopeManagement;
    }

    public void setProjectScopeManagement(String projectScopeManagement) {
        this.projectScopeManagement = projectScopeManagement;
    }

    public String getProjectScheduleManagement() {
        return projectScheduleManagement;
    }

    public void setProjectScheduleManagement(String projectScheduleManagement) {
        this.projectScheduleManagement = projectScheduleManagement;
    }

    public String getProjectCostManagement() {
        return projectCostManagement;
    }

    public void setProjectCostManagement(String projectCostManagement) {
        this.projectCostManagement = projectCostManagement;
    }

    public String getProjectQualityManagement() {
        return projectQualityManagement;
    }

    public void setProjectQualityManagement(String projectQualityManagement) {
        this.projectQualityManagement = projectQualityManagement;
    }

    public String getProjectResourceManagement() {
        return projectResourceManagement;
    }

    public void setProjectResourceManagement(String projectResourceManagement) {
        this.projectResourceManagement = projectResourceManagement;
    }

    public String getProjectCommunicationsManagement() {
        return projectCommunicationsManagement;
    }

    public void setProjectCommunicationsManagement(String projectCommunicationsManagement) {
        this.projectCommunicationsManagement = projectCommunicationsManagement;
    }

    public String getProjectRiskManagement() {
        return projectRiskManagement;
    }

    public void setProjectRiskManagement(String projectRiskManagement) {
        this.projectRiskManagement = projectRiskManagement;
    }

    public String getProjectProcurementManagement() {
        return projectProcurementManagement;
    }

    public void setProjectProcurementManagement(String projectProcurementManagement) {
        this.projectProcurementManagement = projectProcurementManagement;
    }

    public String getProjectStakeholderManagement() {
        return projectStakeholderManagement;
    }

    public void setProjectStakeholderManagement(String projectStakeholderManagement) {
        this.projectStakeholderManagement = projectStakeholderManagement;
    }

}