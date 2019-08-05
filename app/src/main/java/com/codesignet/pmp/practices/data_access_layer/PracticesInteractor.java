package com.codesignet.pmp.practices.data_access_layer;

import android.content.Context;

import com.codesignet.pmp.app.Config;
import com.codesignet.pmp.basics.BaseInteractour;
import com.codesignet.pmp.exam.data_access_layer.pojos.ReportObject;
import com.codesignet.pmp.notes.data_access_layer.database.NotesDatabaseHelper;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;
import com.codesignet.pmp.notes.data_access_layer.pojos.Question;
import com.codesignet.pmp.practices.data_access_layer.database_manager.PracticesDatabaseHelper;
import com.codesignet.pmp.practices.data_access_layer.network_manager.API;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesObject;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesQuestionsItem;
import com.codesignet.pmp.practices.view.PracticesCallBackInterface;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PracticesInteractor extends BaseInteractour {

    private API retrofit;
    private PracticesDatabaseHelper databaseHelper;

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


    public void getLevelQuestion(PracticesCallBackInterface callBack, String accessToken, PracticesObject practicesObject) {
        createAPICall(accessToken);
        retrofit.getQuestionsByLevel(practicesObject)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onSuccess, callBack::onFailure);
    }

    public void report(PracticesCallBackInterface callBack, String accessToken, ReportObject obj) {
        createAPICall(accessToken);
        retrofit.reportQuestion(obj)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onAddSuccess, callBack::onFailure);
    }

    public boolean addQuestions(ArrayList<PracticesQuestionsItem> questions, Context mContext) {
        databaseHelper = new PracticesDatabaseHelper(mContext);
            return  databaseHelper.addPracticesQuestions(questions);
    }

    public void addNote(Note note, PracticesQuestionsItem questionsItem, Context mContext) {
        NotesDatabaseHelper databaseHelper = new NotesDatabaseHelper(mContext);
        note.setQuestion(createQuestion(questionsItem));
        databaseHelper.insertNoteWithQuestion(note);
    }

    private Question createQuestion(PracticesQuestionsItem questionsItem) {
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

    public void dropTable(Context mContext) {
        databaseHelper = new PracticesDatabaseHelper(mContext);
        databaseHelper.onUpgrade(null, 0, 0);
    }

    public PracticesQuestionsItem getNextQuestionFromData(Boolean isProcessGroup, String value, String level, int previuesQuestionId, Context mContext) {
        databaseHelper = new PracticesDatabaseHelper(mContext);
        return databaseHelper.getQuestion(isProcessGroup, value, level, previuesQuestionId);
    }

    public boolean updateQuestionUser(PracticesQuestionsItem note, Context mContext) {
        databaseHelper = new PracticesDatabaseHelper(mContext);
        return databaseHelper.updateUserAnswer(note);
    }

    public void getUserLevels(String accessToken, PracticesCallBackInterface callBack) {
        createAPICall(accessToken);
        retrofit.getLevels()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onLevelResponse, callBack::onFailure);
    }
    public float getSolvedQuestionNumber(Boolean isProcessGroup, String value, String level, Context mContext) {
        databaseHelper = new PracticesDatabaseHelper(mContext);
        return databaseHelper.curentQuestionNumber(isProcessGroup, value, level);
    }

    public PracticesQuestionsItem getOneQuestion(Context mContext) {
        databaseHelper = new PracticesDatabaseHelper(mContext);
        return databaseHelper.getOneQuestion();
    }
}
