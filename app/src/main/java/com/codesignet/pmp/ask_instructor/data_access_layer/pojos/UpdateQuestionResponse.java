package com.codesignet.pmp.ask_instructor.data_access_layer.pojos;

import com.google.gson.annotations.SerializedName;

public class UpdateQuestionResponse {

    @SerializedName("reason")
    private String reason;

    @SerializedName("success")
    private boolean success;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}