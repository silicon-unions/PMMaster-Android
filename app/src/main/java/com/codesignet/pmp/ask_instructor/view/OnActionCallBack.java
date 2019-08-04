package com.codesignet.pmp.ask_instructor.view;

import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.QuestionObject;

public interface OnActionCallBack {
    void onAddQuestion(QuestionObject questionItem);

    void showAskNewQuestionDialog();
}
