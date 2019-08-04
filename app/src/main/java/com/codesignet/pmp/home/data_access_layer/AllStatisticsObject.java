package com.codesignet.pmp.home.data_access_layer;

public class AllStatisticsObject {
    private int wrongAnswers;
    private int unresolvedAnswers;
    private int correctAnswers;
    private int allQuestions;


    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public int getUnresolvedAnswers() {
        return unresolvedAnswers;
    }

    public void setUnresolvedAnswers(int unresolvedAnswers) {
        this.unresolvedAnswers = unresolvedAnswers;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getAllQuestions() {
        return allQuestions;
    }

    public void setAllQuestions(int allQuestions) {
        this.allQuestions = allQuestions;
    }
}
