package com.codesignet.pmp.exam.view;

import com.codesignet.pmp.basics.BaseView;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionListResponse;

public interface ExamView extends BaseView {
    void onSuccess(QuestionListResponse response);
    void onFailure(Throwable e);
    void onShowLoader();
    void onHideLoader();
}
