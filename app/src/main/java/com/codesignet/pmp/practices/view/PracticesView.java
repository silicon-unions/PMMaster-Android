package com.codesignet.pmp.practices.view;

import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.basics.BaseView;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesListResponse;
import com.codesignet.pmp.practices.data_access_layer.pojos.UserLevels;

public interface PracticesView extends BaseView {
    void onSuccess(PracticesListResponse response);

    void onSuccess(BaseResponse response);

    void onFailure(Throwable e);

    void onLevelClicked(int level);

    void onLevelsReceived(UserLevels userLevels);
}
