package com.codesignet.pmp.ask_instructor.view;

import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.AskInstructorResponse;
import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.UpdateQuestionResponse;
import com.codesignet.pmp.basics.BaseResponse;

public interface AskPresenterCallBack {
    void onAskListReceived(AskInstructorResponse response);

    void onAddedNewQuestions(BaseResponse response);

    void onUpdateQuestions(UpdateQuestionResponse response);

    void onFailed(Throwable throwable);
}
