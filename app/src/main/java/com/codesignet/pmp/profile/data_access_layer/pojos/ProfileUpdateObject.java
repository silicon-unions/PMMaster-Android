package com.codesignet.pmp.profile.data_access_layer.pojos;

public class ProfileUpdateObject {

    private String name;
    private String password;

    public ProfileUpdateObject(String name) {
        setPassword(name);
    }

    public ProfileUpdateObject(String name, String password) {
        setName(name);
        setPassword(password);
    }

    public ProfileUpdateObject() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
