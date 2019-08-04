package com.codesignet.pmp.Authentication.data_access_layer.modle;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("level")
    private String level;

    @SerializedName("api_token")
    private String apiToken;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}