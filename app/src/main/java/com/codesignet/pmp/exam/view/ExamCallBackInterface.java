package com.codesignet.pmp.exam.view;

import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionListResponse;

public interface ExamCallBackInterface {
    void onSuccess(QuestionListResponse response);
    void onAddSuccess(BaseResponse response);
    void onFailure(Throwable e);
}
