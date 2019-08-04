package com.codesignet.pmp.practices.data_access_layer.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLevels {
    @SerializedName("levels")
    private Levels levels;
    @SerializedName("success")
    private Boolean success;

    public Levels getLevels() {
        return levels;
    }

    public void setLevels(Levels levels) {
        this.levels = levels;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}