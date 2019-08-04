package com.codesignet.pmp.Authentication.data_access_layer.modle;

public class LinkedInsObject {
    private String name;
    private String email;
    private String linkedInId;


    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLinkedInId() {
        return linkedInId;
    }

    public void setLinkedInId(String linkedInId) {
        this.linkedInId = linkedInId;
    }
}
