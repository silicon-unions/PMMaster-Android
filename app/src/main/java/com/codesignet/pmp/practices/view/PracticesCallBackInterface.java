package com.codesignet.pmp.practices.view;

import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesListResponse;
import com.codesignet.pmp.practices.data_access_layer.pojos.UserLevels;

public interface PracticesCallBackInterface {
    void onSuccess(PracticesListResponse response);
    void onAddSuccess(BaseResponse response);
    void onFailure(Throwable e);
    void onLevelResponse(UserLevels userLevels);
}
