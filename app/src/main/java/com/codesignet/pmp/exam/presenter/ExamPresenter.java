package com.codesignet.pmp.exam.presenter;

import android.content.Context;

import com.codesignet.pmp.basics.BasePresenter;
import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.exam.data_access_layer.ExamInteractor;
import com.codesignet.pmp.exam.data_access_layer.pojos.ExamObject;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionListResponse;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionsItem;
import com.codesignet.pmp.exam.data_access_layer.pojos.ReportObject;
import com.codesignet.pmp.exam.view.ExamCallBackInterface;
import com.codesignet.pmp.exam.view.ExamView;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;

public class ExamPresenter extends BasePresenter<ExamView, ExamInteractor>
        implements ExamCallBackInterface {

    private ExamInteractor examInteractor;
    private ExamView mView;

    public ExamPresenter(ExamView mView, ExamInteractor mInteractor) {
        this.mView = mView;
        examInteractor = mInteractor;
    }

    public void getQuestionList(String accessToken, ExamObject examObject) {
        mView.onShowLoader();
        examInteractor.getExamQuestions(this, accessToken, examObject);
    }

    public void addNewNoteToAPI(Note note, QuestionsItem questions, Context mContext) {

        examInteractor.addNote(note, questions, mContext);
    }

    public void reportQuestion(ReportObject obj, String accessToken) {
        mView.onShowLoader();
        examInteractor.report(this, accessToken, obj);
    }

    @Override
    public void onSuccess(QuestionListResponse response) {
        mView.onHideLoader();
        mView.onSuccess(response);
    }

    @Override
    public void onFailure(Throwable e) {
        mView.onError(e);
    }

    @Override
    public void onAddSuccess(BaseResponse response) {
        mView.onHideLoader();
        mView.showMessage(response.getReason());
    }
}
