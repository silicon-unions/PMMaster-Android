package com.codesignet.pmp.Authentication.presenter;

import com.codesignet.pmp.Authentication.data_access_layer.AuthenticationInteractor;
import com.codesignet.pmp.Authentication.data_access_layer.modle.AuthenticationRequest;
import com.codesignet.pmp.Authentication.data_access_layer.modle.AuthenticationResponse;
import com.codesignet.pmp.Authentication.data_access_layer.modle.FaceBookObject;
import com.codesignet.pmp.Authentication.data_access_layer.modle.ForgetPasswordResponse;
import com.codesignet.pmp.Authentication.data_access_layer.modle.LinkedInsObject;
import com.codesignet.pmp.Authentication.view.AuthenticationPresenterInterface;
import com.codesignet.pmp.Authentication.view.AuthenticationView;
import com.codesignet.pmp.basics.BasePresenter;


public class AuthenticationPresenter extends BasePresenter<AuthenticationView, AuthenticationInteractor>
        implements AuthenticationPresenterInterface {

    private AuthenticationInteractor mInteractor;
    private AuthenticationView mView;

    public AuthenticationPresenter(AuthenticationView mView, AuthenticationInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    public void submitLoginUser(AuthenticationRequest request) {
        mInteractor.submitLogin(request, this);
    }

    public void submitNewUser(AuthenticationRequest user) {
        mInteractor.submitNewUserToAPI(user, this);
    }

    public void getPassword(String email) {
        mInteractor.getMyPassword(email, this);
    }

    @Override
    public void onFailure(Throwable e) {
        mView.onFailure(e.getMessage());
    }

    @Override
    public void onSuccess(AuthenticationResponse response) {
        mView.onSuccess(response);
    }

    @Override
    public void onSuccess(ForgetPasswordResponse response) {
        mView.hideLoader();
        if (response.getReason().equals(""))
            mView.showMessage(response.getMessage());
        else
            mView.onFailure(response.getReason());
    }

    public void loginWithFacebook(FaceBookObject faceBookObject) {
        mView.showLoader();
        mInteractor.submitLoginWithFacebook(faceBookObject, this);
    }

    public void signUpWithFacebook(FaceBookObject faceBookObject) {
        mView.showLoader();
        mInteractor.submitSignUpWithFacebook(faceBookObject, this);
    }

    public void loginWithLinkedin(LinkedInsObject linkedInsObject) {
        mView.showLoader();
        mInteractor.submitLoginWithLinkedIn(linkedInsObject, this);
    }

    public void signUpWithLinkedIn(LinkedInsObject linkedInsObject) {
        mView.showLoader();
        mInteractor.submitSignUpWithLinked(linkedInsObject, this);
    }
}
