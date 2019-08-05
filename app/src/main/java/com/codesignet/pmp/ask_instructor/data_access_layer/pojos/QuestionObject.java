package com.codesignet.pmp.ask_instructor.data_access_layer.pojos;

public class QuestionObject {

    private String question;

    public QuestionObject(String question) {
        setQuestion(question);
    }

    public String getQuestion() {
        return question;
    }

    private void setQuestion(String question) {
        this.question = question;
    }
}
