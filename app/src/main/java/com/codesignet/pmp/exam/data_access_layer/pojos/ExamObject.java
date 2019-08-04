package com.codesignet.pmp.exam.data_access_layer.pojos;

public class ExamObject {
    private int numberOfQuestions;

    public ExamObject(int examQuestionsNumber) {
        setNumberOfQuestions(examQuestionsNumber);
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
}
