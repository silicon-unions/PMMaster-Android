package com.codesignet.pmp.profile.presenter;

import com.codesignet.pmp.basics.BasePresenter;
import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.profile.data_access_layer.ProfileInteractor;
import com.codesignet.pmp.profile.data_access_layer.pojos.ProfileResponse;
import com.codesignet.pmp.profile.data_access_layer.pojos.ProfileUpdateObject;
import com.codesignet.pmp.profile.view.ProfilePresenterCallBack;
import com.codesignet.pmp.profile.view.ProfileView;

import java.io.File;

import okhttp3.ResponseBody;

public class ProfilePresenter extends BasePresenter<ProfileView, ProfileInteractor>
        implements ProfilePresenterCallBack {

    private ProfileInteractor mInteractor;
    private ProfileView mView;

    public ProfilePresenter(ProfileView mView, ProfileInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    public void getUserData(String accessToken) {
        mView.showLoader();
        mInteractor.getUserDataFromAPI(accessToken, this);
    }

    public void updateProfileData(String accessToken, ProfileUpdateObject newUser) {
        mView.showLoader();
        mInteractor.updateProfileData(accessToken, newUser, this);
    }

    public void updateProfileImage(String accessToken, File fileURL) {
        mView.showLoader();
        mInteractor.updateProfilePicture(accessToken, fileURL, this);
    }

    public void getUserImage(String accessToken) {
        mView.showLoader();
        mInteractor.downloadImage(accessToken, this);
    }

    @Override
    public void onImagesDownloaded(ResponseBody responseBody) {
        mView.hideLoader();
        mView.showUserImage(responseBody);
    }

    @Override
    public void onProfileDataReceivedSuccessfully(ProfileResponse profileResponse) {
        if (mView == null)
            return;
        mView.hideLoader();
        mView.showUserData(profileResponse.getUser());
    }

    @Override
    public void onUpdateSuccess(BaseResponse response) {
        if (mView == null)
            return;
        mView.hideLoader();
        mView.refresh();
        mView.showMessage(response.getReason());
    }

    @Override
    public void onUploadSuccess(BaseResponse response) {
        if (mView == null)
            return;
        mView.hideLoader();
        mView.refresh();
    }

    @Override
    public void onFailed(Throwable throwable) {
        if (mView == null)
            return;
        mView.hideLoader();
        mView.onError(throwable);
    }
}
