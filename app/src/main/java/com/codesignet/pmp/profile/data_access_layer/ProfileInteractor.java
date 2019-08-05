package com.codesignet.pmp.profile.data_access_layer;

import com.codesignet.pmp.app.Config;
import com.codesignet.pmp.basics.BaseInteractour;
import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.profile.data_access_layer.pojos.ProfileUpdateObject;
import com.codesignet.pmp.profile.view.ProfilePresenterCallBack;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileInteractor extends BaseInteractour {

    private API retrofit;


    private void createAPICall(String accessToken) {
        retrofit = new Retrofit.Builder()
                .client(PMPOkHttpClient(accessToken))
                .baseUrl(Config.BASE_API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(API.class);
    }

    private OkHttpClient PMPOkHttpClient(String accessToken) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .addHeader("Accept", "application/json")
                    .build();
            return chain.proceed(newRequest);
        }).build();
        return client;
    }

    public void getUserDataFromAPI(String accessToken, ProfilePresenterCallBack callBack) {
        createAPICall(accessToken);
        retrofit.getUserData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onProfileDataReceivedSuccessfully, callBack::onFailed);
    }

    public void updateProfileData(String accessToken, ProfileUpdateObject newUser, ProfilePresenterCallBack callBack) {
        createAPICall(accessToken);
        retrofit.updateUserData(newUser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onUploadSuccess, callBack::onFailed);
    }

    public void updateProfilePicture(String accessToken, File filePath, ProfilePresenterCallBack callBack) {
        createAPICall(accessToken);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/png"), filePath);
        MultipartBody.Part body = MultipartBody.Part.createFormData("profilePicture", filePath.getName(), reqFile);

        Call<BaseResponse> call = retrofit.uploadFile(body);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                callBack.onUploadSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callBack.onFailed(t);
            }
        });
    }

    public void downloadImage(String accessToken, ProfilePresenterCallBack callBack) {
        createAPICall(accessToken);
        Call<ResponseBody> call = retrofit.getImage();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callBack.onImagesDownloaded(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.onFailed(t);
            }
        });
    }

}
