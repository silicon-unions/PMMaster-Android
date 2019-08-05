package com.codesignet.pmp.practices.data_access_layer.pojos;

public class LevelObject {
    private String levelName;
    private int questionsNumber;

    public int getQuestionsNumber() {
        return questionsNumber;
    }

    public void setQuestionsNumber(int questionsNumber) {
        this.questionsNumber = questionsNumber;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
