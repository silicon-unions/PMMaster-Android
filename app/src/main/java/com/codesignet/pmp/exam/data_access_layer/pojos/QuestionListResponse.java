package com.codesignet.pmp.exam.data_access_layer.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuestionListResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("questions")
    private ArrayList<QuestionsItem> questions;

    @SerializedName("exam_id")
    private int examId;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<QuestionsItem> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuestionsItem> questions) {
        this.questions = questions;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }
}