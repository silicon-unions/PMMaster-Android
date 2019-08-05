package com.codesignet.pmp.Authentication.data_access_layer.modle;

import com.google.gson.annotations.SerializedName;

public class ForgetPasswordResponse {

    @SerializedName("reason")
    private String reason;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}