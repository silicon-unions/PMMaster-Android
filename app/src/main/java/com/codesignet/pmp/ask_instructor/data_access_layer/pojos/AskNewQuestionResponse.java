package com.codesignet.pmp.ask_instructor.data_access_layer.pojos;

import com.google.gson.annotations.SerializedName;

public class AskNewQuestionResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("instructorQuestions")
    private AskInstructorQuestionItem instructorQuestions;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public AskInstructorQuestionItem getInstructorQuestions() {
        return instructorQuestions;
    }

    public void setInstructorQuestions(AskInstructorQuestionItem instructorQuestions) {
        this.instructorQuestions = instructorQuestions;
    }

}