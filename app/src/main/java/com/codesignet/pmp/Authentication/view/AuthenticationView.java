package com.codesignet.pmp.Authentication.view;

import com.codesignet.pmp.Authentication.data_access_layer.modle.AuthenticationResponse;
import com.codesignet.pmp.basics.BaseView;

public interface AuthenticationView extends BaseView {
    void onSuccess(AuthenticationResponse response);
    void onFailure(String message);
    void showLoader();
    void hideLoader();
}
