package com.codesignet.pmp.practices.presenter;

import android.content.Context;

import com.codesignet.pmp.basics.BasePresenter;
import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.exam.data_access_layer.ExamInteractor;
import com.codesignet.pmp.exam.data_access_layer.pojos.ReportObject;
import com.codesignet.pmp.exam.view.ExamView;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;
import com.codesignet.pmp.practices.data_access_layer.PracticesInteractor;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesListResponse;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesObject;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesQuestionsItem;
import com.codesignet.pmp.practices.data_access_layer.pojos.UserLevels;
import com.codesignet.pmp.practices.view.PracticesCallBackInterface;
import com.codesignet.pmp.practices.view.PracticesView;

import java.util.ArrayList;

public class PracticesPresenter extends BasePresenter<ExamView, ExamInteractor>
        implements PracticesCallBackInterface {

    private PracticesInteractor mInteractor;
    private PracticesView mView;

    public PracticesPresenter(PracticesView mView, PracticesInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }
    public void getLevelQuestions(PracticesObject practicesObject, String accessToken) {
        mInteractor.getLevelQuestion(this, accessToken, practicesObject);
    }

    @Override
    public void onSuccess(PracticesListResponse response) {
        mView.onSuccess(response);
    }

    @Override
    public void onAddSuccess(BaseResponse response) {
        mView.onSuccess(response);
    }

    @Override
    public void onFailure(Throwable e) {
        mView.onFailure(e);
    }

    public void reportQuestion(ReportObject obj, String accessToken) {
        mInteractor.report(this, accessToken, obj);
    }

    public boolean addQuestions(ArrayList<PracticesQuestionsItem> questions, Context mContext) {
        return mInteractor.addQuestions(questions, mContext);
    }

    public void dropData(Context mContext) {
        mInteractor.dropTable(mContext);
    }

    public PracticesQuestionsItem getNextQuestionFromData(Boolean isProcessGroup, String value, String level, int previuesQuestionId, Context mContext) {
        return mInteractor.getNextQuestionFromData(isProcessGroup, value, level, previuesQuestionId, mContext);
    }
    public float getSolvedQuestionNumber(Boolean isProcessGroup, String value, String level, Context mContext) {
        return mInteractor.getSolvedQuestionNumber(isProcessGroup, value, level, mContext);
    }

    public void addNewNoteToAPI(Note note, PracticesQuestionsItem questions, Context mContext) {
        mInteractor.addNote(note, questions, mContext);
    }

    public boolean updateUserAnswer(PracticesQuestionsItem note, Context mContext) {
        return mInteractor.updateQuestionUser(note, mContext);
    }

    public void getUserLevels(String accessToken) {
        mInteractor.getUserLevels(accessToken, this);
    }

    @Override
    public void onLevelResponse(UserLevels userLevels) {
        mView.onLevelsReceived(userLevels);
    }

    public PracticesQuestionsItem getQuestion(Context applicationContext) {
        return mInteractor.getOneQuestion(applicationContext);
    }
}
