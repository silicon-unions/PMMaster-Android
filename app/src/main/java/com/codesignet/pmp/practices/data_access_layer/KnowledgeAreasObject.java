package com.codesignet.pmp.practices.data_access_layer;

public class KnowledgeAreasObject {
    private String KnowledgeAreasName;

    private int KnowledgeAreasLevel;

    public KnowledgeAreasObject(String KnowledgeAreasName,int KnowledgeAreasLevel){
        setKnowledgeAreasName(KnowledgeAreasName);
        setKnowledgeAreasLevel(KnowledgeAreasLevel);
    }
    public int getKnowledgeAreasLevel() {
        return KnowledgeAreasLevel;
    }

    public void setKnowledgeAreasLevel(int KnowledgeAreasLevel) {
        this.KnowledgeAreasLevel = KnowledgeAreasLevel;
    }

    public String getKnowledgeAreasName() {
        return KnowledgeAreasName;
    }

    public void setKnowledgeAreasName(String KnowledgeAreasName) {
        this.KnowledgeAreasName = KnowledgeAreasName;
    }
}
