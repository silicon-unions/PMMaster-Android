package com.codesignet.pmp.profile.view;

import com.codesignet.pmp.basics.BaseView;
import com.codesignet.pmp.profile.data_access_layer.pojos.User;

import okhttp3.ResponseBody;

public interface ProfileView extends BaseView {
    void showLoader();

    void hideLoader();

    void showUserData(User user);

    void showUserImage(ResponseBody bitmap);

    void refresh();
}
