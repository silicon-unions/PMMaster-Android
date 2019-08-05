package com.codesignet.pmp.practices.data_access_layer.pojos;

public class PracticesObject {
    private int level;
    private String processGroup;
    private String knowledgeArea;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getProcessGroup() {
        return processGroup;
    }

    public void setProcessGroup(String processGroup) {
        this.processGroup = processGroup;
    }

    public String getKnowledgeArea() {
        return knowledgeArea;
    }

    public void setKnowledgeArea(String knowledgeArea) {
        this.knowledgeArea = knowledgeArea;
    }
}
