package com.codesignet.pmp.Authentication.data_access_layer.modle;

import com.google.gson.annotations.SerializedName;

public class AuthenticationResponse {

    @SerializedName("reason")
    private String reason;

    @SerializedName("success")
    private boolean success;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("user")
    private User user;

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

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}