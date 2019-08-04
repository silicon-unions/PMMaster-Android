package com.codesignet.pmp.profile.view;

import com.codesignet.pmp.profile.data_access_layer.pojos.ProfileUpdateObject;

import java.io.File;

public interface OnUpdateCallBack {
    void onUpdateClicked(ProfileUpdateObject newData);

    void onChangePasswordButtonClicked();

    void onUploadClicked(File file);
}
