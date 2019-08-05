package com.codesignet.pmp.practices.data_access_layer.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PracticesListResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("questions")
    private ArrayList<PracticesQuestionsItem> questions;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<PracticesQuestionsItem> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<PracticesQuestionsItem> questions) {
        this.questions = questions;
    }
}