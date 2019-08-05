package com.codesignet.pmp.Authentication.view;

import com.codesignet.pmp.Authentication.data_access_layer.modle.AuthenticationResponse;
import com.codesignet.pmp.Authentication.data_access_layer.modle.ForgetPasswordResponse;

public interface AuthenticationPresenterInterface {
    void onSuccess(AuthenticationResponse response);
    void onSuccess(ForgetPasswordResponse response);
    void onFailure(Throwable e);
}
