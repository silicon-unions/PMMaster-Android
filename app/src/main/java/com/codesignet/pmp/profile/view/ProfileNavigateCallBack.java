package com.codesignet.pmp.profile.view;

import com.codesignet.pmp.profile.data_access_layer.pojos.User;

public interface ProfileNavigateCallBack {
    void gotoEditProfileScreen();

    void gotoProfileData(User user);

    void gotoProfileData();

    void gotoChangePasswordScreen();

    void gotoHomeScreen();
}
