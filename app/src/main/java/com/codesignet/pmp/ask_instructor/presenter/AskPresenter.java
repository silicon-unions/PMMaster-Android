package com.codesignet.pmp.ask_instructor.presenter;

import com.codesignet.pmp.ask_instructor.data_access_layer.AskInteractor;
import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.AskInstructorResponse;
import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.QuestionObject;
import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.QuestionObjectID;
import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.UpdateQuestionResponse;
import com.codesignet.pmp.ask_instructor.view.AskPresenterCallBack;
import com.codesignet.pmp.ask_instructor.view.AskView;
import com.codesignet.pmp.basics.BasePresenter;
import com.codesignet.pmp.basics.BaseResponse;

public class AskPresenter extends BasePresenter<AskView, AskInteractor>
        implements AskPresenterCallBack {
    private AskInteractor mInteractor;
    private AskView mView;

    public AskPresenter(AskView mView, AskInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    public void getAskList(String accessToken) {
        mView.showLoader();
        mInteractor.getAskListFromAPI(accessToken, this);
    }

    public void addNewQuestion(String accessToken, QuestionObject question) {
        mView.showLoader();
        mInteractor.addNewToAPI(accessToken, question, this);
    }

    public void deleteQuestion(String accessToken, QuestionObjectID question) {
        mView.showLoader();
        mInteractor.deleteQuestionFromAPI(accessToken, question, this);
    }

    public void updateQuestionStatus(String accessToken, QuestionObjectID question) {
        mView.showLoader();
        mInteractor.isQuestionSeen(accessToken, question, this);
    }

    @Override
    public void onAskListReceived(AskInstructorResponse response) {
        if (mView == null)
            return;
        mView.hideLoader();
        mView.showAllQuestion(response.getInstructorQuestions());
    }

    @Override
    public void onAddedNewQuestions(BaseResponse response) {
        if (mView == null)
            return;
        mView.hideLoader();
        mView.onQuestionAdded(response.getReason());
    }

    @Override
    public void onUpdateQuestions(UpdateQuestionResponse response) {
        if (mView == null)
            return;
        mView.hideLoader();
    }

    @Override
    public void onFailed(Throwable throwable) {
        if (mView == null)
            return;
        mView.hideLoader();
        mView.onError(throwable);
    }
}
