package com.codesignet.pmp.Authentication.data_access_layer;


import com.codesignet.pmp.Authentication.data_access_layer.modle.AuthenticationRequest;
import com.codesignet.pmp.Authentication.data_access_layer.modle.AuthenticationResponse;
import com.codesignet.pmp.Authentication.data_access_layer.modle.FaceBookObject;
import com.codesignet.pmp.Authentication.data_access_layer.modle.ForgetPasswordResponse;
import com.codesignet.pmp.Authentication.data_access_layer.modle.LinkedInsObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {

    @POST("api/login")
    Observable<AuthenticationResponse> getUserAuthentication(@Body AuthenticationRequest request);

    @POST("api/register")
    Observable<AuthenticationResponse> addNewUser(@Body AuthenticationRequest user);

    @POST("api/forgetPassword")
    Observable<ForgetPasswordResponse> ForgetPassword(@Body String email);

    @POST("api/loginWithFacebook")
    Observable<AuthenticationResponse> loginWithFacebook(@Body FaceBookObject faceBookObject);

    @POST("api/signupWithFacebook")
    Observable<AuthenticationResponse> signUpWithFacebook(@Body FaceBookObject faceBookObject);

    @POST("api/loginWithLinkedIn")
    Observable<AuthenticationResponse>  signInWithLinkedIn(@Body LinkedInsObject linkedInsObject);

    @POST("api/signupWithLinkedIn")
    Observable<AuthenticationResponse>  signUpWithLinkedIn(@Body LinkedInsObject linkedInsObject);
}
