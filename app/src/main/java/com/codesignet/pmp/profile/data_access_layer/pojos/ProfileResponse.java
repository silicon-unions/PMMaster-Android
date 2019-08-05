package com.codesignet.pmp.profile.data_access_layer.pojos;

import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("user")
    private User user;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}