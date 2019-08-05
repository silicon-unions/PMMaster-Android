package com.codesignet.pmp.home.data_access_layer;

import android.content.Context;

import com.codesignet.pmp.app.Config;
import com.codesignet.pmp.basics.BaseInteractour;
import com.codesignet.pmp.home.presenter.HomePresenter;
import com.codesignet.pmp.home.view.onLevelSynced;
import com.codesignet.pmp.notes.data_access_layer.database.NotesDatabaseHelper;
import com.codesignet.pmp.notes.data_access_layer.network.API;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;
import com.codesignet.pmp.notes.data_access_layer.pojos.SyncNodeRequest;
import com.codesignet.pmp.notes.view.NotesPresenterCallBack;
import com.codesignet.pmp.practices.data_access_layer.pojos.UserLevels;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeInteractor extends BaseInteractour {
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

    public ArrayList<Note> getAllNotes(Context mContext) {
        databaseHelper = new NotesDatabaseHelper(mContext);
        return databaseHelper.getAllSyncNotes();
    }

    public void updateNotesTable(Context mContext, List<Note> notes) {
        databaseHelper = new NotesDatabaseHelper(mContext);
        databaseHelper.updateNotesTable(notes);
    }

    public void syncAllNotes(NotesPresenterCallBack callBack, SyncNodeRequest allNotes, String accessToken) {
        createAPICall(accessToken);
        retrofit.syncAllNotes(allNotes)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onNotesSynced, callBack::onFailure);
    }

    public void unpdateUserLevel(String accessToken, UserLevels userLevels, onLevelSynced callBack) {
        createAPICall(accessToken);
        retrofit.syncLevels(userLevels)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callBack::onUpdateSuccess, callBack::onFailure);
    }
}
