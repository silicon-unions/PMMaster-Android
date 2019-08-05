package com.codesignet.pmp.ask_instructor.data_access_layer.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AskInstructorResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("instructorQuestions")
    private ArrayList<AskInstructorQuestionItem> instructorQuestions;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<AskInstructorQuestionItem> getInstructorQuestions() {
        return instructorQuestions;
    }

    public void setInstructorQuestions(ArrayList<AskInstructorQuestionItem> instructorQuestions) {
        this.instructorQuestions = instructorQuestions;
    }
}