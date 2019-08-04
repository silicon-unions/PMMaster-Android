package com.codesignet.pmp.practices.data_access_layer.pojos;

import com.google.gson.annotations.SerializedName;

public class PracticesQuestionsItem {

    @SerializedName("level")
    private String level;

    @SerializedName("answerA4")
    private String answerA4;

    @SerializedName("justificationA")
    private String justificationA;

    @SerializedName("answerA3")
    private String answerA3;

    @SerializedName("processGroup")
    private String processGroup;

    @SerializedName("answerE2")
    private String answerE2;

    @SerializedName("answerE1")
    private String answerE1;

    @SerializedName("answerE4")
    private String answerE4;

    @SerializedName("answer")
    private String answer;

    @SerializedName("justificationE")
    private String justificationE;

    @SerializedName("answerE3")
    private String answerE3;

    @SerializedName("answerA2")
    private String answerA2;

    @SerializedName("answerA1")
    private String answerA1;

    @SerializedName("knowledgeArea")
    private String knowledgeArea;

    @SerializedName("questionE")
    private String questionE;

    @SerializedName("id")
    private int id;

    @SerializedName("questionA")
    private String questionA;

    private String userAnswer = "0";

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAnswerA4() {
        return answerA4;
    }

    public void setAnswerA4(String answerA4) {
        this.answerA4 = answerA4;
    }

    public String getJustificationA() {
        return justificationA;
    }

    public void setJustificationA(String justificationA) {
        this.justificationA = justificationA;
    }

    public String getAnswerA3() {
        return answerA3;
    }

    public void setAnswerA3(String answerA3) {
        this.answerA3 = answerA3;
    }

    public String getProcessGroup() {
        return processGroup;
    }

    public void setProcessGroup(String processGroup) {
        this.processGroup = processGroup;
    }

    public String getAnswerE2() {
        return answerE2;
    }

    public void setAnswerE2(String answerE2) {
        this.answerE2 = answerE2;
    }

    public String getAnswerE1() {
        return answerE1;
    }

    public void setAnswerE1(String answerE1) {
        this.answerE1 = answerE1;
    }

    public String getAnswerE4() {
        return answerE4;
    }

    public void setAnswerE4(String answerE4) {
        this.answerE4 = answerE4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getJustificationE() {
        return justificationE;
    }

    public void setJustificationE(String justificationE) {
        this.justificationE = justificationE;
    }

    public String getAnswerE3() {
        return answerE3;
    }

    public void setAnswerE3(String answerE3) {
        this.answerE3 = answerE3;
    }

    public String getAnswerA2() {
        return answerA2;
    }

    public void setAnswerA2(String answerA2) {
        this.answerA2 = answerA2;
    }

    public String getAnswerA1() {
        return answerA1;
    }

    public void setAnswerA1(String answerA1) {
        this.answerA1 = answerA1;
    }

    public String getKnowledgeArea() {
        return knowledgeArea;
    }

    public void setKnowledgeArea(String knowledgeArea) {
        this.knowledgeArea = knowledgeArea;
    }

    public String getQuestionE() {
        return questionE;
    }

    public void setQuestionE(String questionE) {
        this.questionE = questionE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionA() {
        return questionA;
    }

    public void setQuestionA(String questionA) {
        this.questionA = questionA;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}