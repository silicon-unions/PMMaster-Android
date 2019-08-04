package com.codesignet.pmp.home.view;

import com.codesignet.pmp.basics.BaseResponse;

public interface onLevelSynced {
    void onUpdateSuccess(BaseResponse response);
    void onFailure(Throwable e);

}
