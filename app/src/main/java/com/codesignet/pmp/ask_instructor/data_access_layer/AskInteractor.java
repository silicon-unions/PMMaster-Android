package com.codesignet.pmp.ask_instructor.data_access_layer;

import com.codesignet.pmp.app.Config;
import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.QuestionObject;
import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.QuestionObjectID;
import com.codesignet.pmp.ask_instructor.view.AskPresenterCallBack;
import com.codesignet.pmp.basics.BaseInteractour;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AskInteractor extends BaseInteractour {

    private API retrofit;

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

    public void getAskListFromAPI(String accessToken, AskPresenterCallBack callBack) {
        createAPICall(accessToken);
        retrofit.getList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onAskListReceived, callBack::onFailed);
    }

    public void addNewToAPI(String accessToken, QuestionObject question, AskPresenterCallBack callBack) {
        createAPICall(accessToken);
        retrofit.askQuestion(question)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onAddedNewQuestions, callBack::onFailed);
    }

    public void deleteQuestionFromAPI(String accessToken, QuestionObjectID question, AskPresenterCallBack callBack) {
        createAPICall(accessToken);
        retrofit.deleteQuestion(question)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onAddedNewQuestions, callBack::onFailed);
    }

    public void isQuestionSeen(String accessToken, QuestionObjectID question, AskPresenterCallBack callBack) {
        createAPICall(accessToken);
        retrofit.updateQuestionStatus(question)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onUpdateQuestions, callBack::onFailed);
    }
}
