package com.codesignet.pmp.Authentication.data_access_layer;

import com.codesignet.pmp.Authentication.data_access_layer.modle.AuthenticationRequest;
import com.codesignet.pmp.Authentication.data_access_layer.modle.FaceBookObject;
import com.codesignet.pmp.Authentication.data_access_layer.modle.LinkedInsObject;
import com.codesignet.pmp.Authentication.presenter.AuthenticationPresenter;
import com.codesignet.pmp.Authentication.view.AuthenticationPresenterInterface;
import com.codesignet.pmp.app.Config;
import com.codesignet.pmp.basics.BaseInteractour;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthenticationInteractor extends BaseInteractour {
    private API retrofit;

    public AuthenticationInteractor() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(API.class);
    }

    public void submitLogin(AuthenticationRequest request, AuthenticationPresenterInterface callBack) {
        retrofit.getUserAuthentication(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onSuccess, callBack::onFailure);
    }

    public void submitNewUserToAPI(AuthenticationRequest request, AuthenticationPresenterInterface callBack) {
        retrofit.addNewUser(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onSuccess, callBack::onFailure);
    }

    public void getMyPassword(String email, AuthenticationPresenterInterface callBack) {
        retrofit.ForgetPassword(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onSuccess, callBack::onFailure);
    }

    public void submitLoginWithFacebook(FaceBookObject faceBookObject, AuthenticationPresenterInterface callBack) {
        retrofit.loginWithFacebook(faceBookObject)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onSuccess, callBack::onFailure);
    }

    public void submitSignUpWithFacebook(FaceBookObject faceBookObject, AuthenticationPresenterInterface callBack) {
        retrofit.signUpWithFacebook(faceBookObject)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onSuccess, callBack::onFailure);
    }

    public void submitLoginWithLinkedIn(LinkedInsObject linkedInsObject, AuthenticationPresenterInterface callBack) {
        retrofit.signInWithLinkedIn(linkedInsObject)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onSuccess, callBack::onFailure);
    }

    public void submitSignUpWithLinked(LinkedInsObject linkedInsObject, AuthenticationPresenterInterface callBack) {
        retrofit.signUpWithLinkedIn(linkedInsObject)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onSuccess, callBack::onFailure);
    }
}
