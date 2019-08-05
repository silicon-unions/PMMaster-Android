package com.codesignet.pmp.exam.data_access_layer.pojos;

import java.io.Serializable;

public class ExamData implements Serializable {
    private int correctAnswers;
    private int wrongAnswers;
    private int flaggedQuestions;
    private int unSolvedQuestions;
    private String examDate;
    private int questionNumber;
    private boolean tenQuestionType;

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

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public boolean isTenQuestionType() {
        return tenQuestionType;
    }

    public void setTenQuestionType(boolean tenQuestionType) {
        this.tenQuestionType = tenQuestionType;
    }
}
