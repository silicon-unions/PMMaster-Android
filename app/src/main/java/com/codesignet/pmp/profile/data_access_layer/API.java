package com.codesignet.pmp.profile.data_access_layer;

import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.profile.data_access_layer.pojos.ProfileResponse;
import com.codesignet.pmp.profile.data_access_layer.pojos.ProfileUpdateObject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {

    @GET("api/user")
    Observable<ProfileResponse> getUserData();

    @POST("api/updateUserData")
    Observable<BaseResponse> updateUserData(@Body ProfileUpdateObject newData);

    @Multipart
    @POST("api/profilePicture")
    Call<BaseResponse> uploadFile(@Part MultipartBody.Part file);

    @GET("api/profilePicture")
    Call<ResponseBody> getImage();

}
