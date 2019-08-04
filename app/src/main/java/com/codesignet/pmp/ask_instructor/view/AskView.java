package com.codesignet.pmp.ask_instructor.view;

import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.AskInstructorQuestionItem;
import com.codesignet.pmp.basics.BaseView;

import java.util.ArrayList;

public interface AskView extends BaseView {

    void showLoader();

    void hideLoader();

    void showAskedQuestionDialog(AskInstructorQuestionItem askInstructorQuestionItem);

    void onQuestionAdded(String reason);

    void onDeleteQuestion(int id, int position);

    void showAllQuestion(ArrayList<AskInstructorQuestionItem> askQuestionsList);

}