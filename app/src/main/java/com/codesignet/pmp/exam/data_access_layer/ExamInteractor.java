package com.codesignet.pmp.exam.data_access_layer;

import android.content.Context;

import com.codesignet.pmp.app.Config;
import com.codesignet.pmp.basics.BaseInteractour;
import com.codesignet.pmp.exam.data_access_layer.network_manager.API;
import com.codesignet.pmp.exam.data_access_layer.pojos.ExamObject;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionsItem;
import com.codesignet.pmp.exam.data_access_layer.pojos.ReportObject;
import com.codesignet.pmp.exam.presenter.ExamPresenter;
import com.codesignet.pmp.exam.view.ExamCallBackInterface;
import com.codesignet.pmp.notes.data_access_layer.database.NotesDatabaseHelper;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;
import com.codesignet.pmp.notes.data_access_layer.pojos.Question;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExamInteractor extends BaseInteractour {

    private API retrofit;
    private NotesDatabaseHelper databaseHelper;


    private void createAPICall(String accessToken) {
        retrofit = new Retrofit.Builder()
                .client(PMPOkHttpClient(accessToken))
                .baseUrl(Config.BASE_API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(API.class);
    }

    private OkHttpClient PMPOkHttpClient(String accessToken) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .addHeader("Accept", "application/json")
                    .build();
            return chain.proceed(newRequest);
        }).build();
        return client;
    }

    public void getExamQuestions(ExamCallBackInterface callBack, String accessToken, ExamObject examObject) {
        createAPICall(accessToken);
        retrofit.getUserExam(examObject)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onSuccess, callBack::onFailure);
    }



    public void report(ExamPresenter callBack, String accessToken, ReportObject obj) {
        createAPICall(accessToken);
        retrofit.reportQuestion(obj)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onAddSuccess, callBack::onFailure);
    }

    public void addNote(Note note, QuestionsItem questionsItem, Context mContext) {
        databaseHelper = new NotesDatabaseHelper(mContext);
        note.setQuestion(createQuestion(questionsItem));
        databaseHelper.insertNoteWithQuestion(note);
    }

    public Question createQuestion(QuestionsItem questionsItem) {
        Question question = new Question();
        question.setLevel(questionsItem.getLevel());
        question.setAnswer(questionsItem.getAnswer());
        question.setAnswerA1(questionsItem.getAnswerA1());
        question.setAnswerA2(questionsItem.getAnswerA2());
        question.setAnswerA3(questionsItem.getAnswerA3());
        question.setAnswerA4(questionsItem.getAnswerA4());
        question.setAnswerE1(questionsItem.getAnswerE1());
        question.setAnswerE2(questionsItem.getAnswerE2());
        question.setAnswerE3(questionsItem.getAnswerE3());
        question.setAnswerE4(questionsItem.getAnswerE4());
        question.setQuestionE(questionsItem.getQuestionE());
        question.setQuestionA(questionsItem.getQuestionA());
        question.setJustificationA(questionsItem.getJustificationA());
        question.setJustificationE(questionsItem.getJustificationE());
        question.setProcessGroup(questionsItem.getProcessGroup());
        question.setKnowledgeArea(questionsItem.getKnowledgeArea());
        question.setId(questionsItem.getId());
        return question;
    }
}
