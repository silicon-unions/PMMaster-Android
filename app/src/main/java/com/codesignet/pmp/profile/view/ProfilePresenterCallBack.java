package com.codesignet.pmp.profile.view;

import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.profile.data_access_layer.pojos.ProfileResponse;

import okhttp3.ResponseBody;

public interface ProfilePresenterCallBack {
    void onProfileDataReceivedSuccessfully(ProfileResponse profileResponse);

    void onUpdateSuccess(BaseResponse response);

    void onUploadSuccess(BaseResponse response);

    void onFailed(Throwable throwable);

    void onImagesDownloaded(ResponseBody bitmap);
}
