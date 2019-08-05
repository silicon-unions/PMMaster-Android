package com.codesignet.pmp.practices.data_access_layer.pojos;

import java.io.Serializable;

public class PracticesData implements Serializable {
    private int correctAnswers;
    private int wrongAnswers;
    private int flaggedQuestions;
    private int unSolvedQuestions;
    private String practicesDate;
    private int questionNumber;

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public int getFlaggedQuestions() {
        return flaggedQuestions;
    }

    public void setFlaggedQuestions(int flaggedQuestions) {
        this.flaggedQuestions = flaggedQuestions;
    }

    public int getUnSolvedQuestions() {
        return unSolvedQuestions;
    }

    public void setUnSolvedQuestions(int unSolvedQuestions) {
        this.unSolvedQuestions = unSolvedQuestions;
    }

    public String getPracticesDate() {
        return practicesDate;
    }

    public void setPracticesDate(String practicesDate) {
        this.practicesDate = practicesDate;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }
}
