package com.codesignet.pmp.home.presenter;

import android.content.Context;
import android.util.Log;

import com.codesignet.pmp.app.Constants;
import com.codesignet.pmp.basics.BasePresenter;
import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.home.data_access_layer.HomeInteractor;
import com.codesignet.pmp.home.view.HomeView;
import com.codesignet.pmp.home.view.onLevelSynced;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;
import com.codesignet.pmp.notes.data_access_layer.pojos.NotesItem;
import com.codesignet.pmp.notes.data_access_layer.pojos.NotesResponse;
import com.codesignet.pmp.notes.data_access_layer.pojos.SyncNodeRequest;
import com.codesignet.pmp.notes.view.NotesPresenterCallBack;
import com.codesignet.pmp.practices.data_access_layer.pojos.UserLevels;

import java.util.ArrayList;

public class HomePresenter extends BasePresenter<HomeView, HomeInteractor> implements NotesPresenterCallBack,onLevelSynced {

    private HomeInteractor mInteractor;
    private HomeView mView;
    private ArrayList<Note> allNotes;
    private Context mContext;

    public HomePresenter(HomeView mView, HomeInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
        allNotes = new ArrayList();
    }

    public void getAllNotesFromAPI(Context mContext) {
        if (mInteractor.getAllNotes(mContext) != null) {
            allNotes.addAll(mInteractor.getAllNotes(mContext));
        } else {
           // mView.showMessage("There is no notes");
        }
    }

    public void syncAllNotes(String accessToken, Context mContext) {
        this.mContext = mContext;
        SyncNodeRequest request = new SyncNodeRequest();
        allNotes.addAll(mInteractor.getAllNotes(mContext));
        request.setNotes(createSyncNoteObject());
        mInteractor.syncAllNotes(this, request, accessToken);
    }

    private ArrayList<NotesItem> createSyncNoteObject() {
        ArrayList<NotesItem> syncNotes = new ArrayList();
        if (allNotes.size() != 0) {
            for (int i = 0; i < allNotes.size(); i++) {
                NotesItem notesItem = new NotesItem();
                notesItem.setNote(allNotes.get(i).getNote());
                notesItem.setIsDeleted(allNotes.get(i).getIsdeleted());
                notesItem.setUpdatedAt(allNotes.get(i).getUpdatedAt());
                notesItem.setId(allNotes.get(i).getId());
                notesItem.setType(allNotes.get(i).getType());
                if (allNotes.get(i).getType().equals(Constants.QUESTION_TYPE))
                    notesItem.setQuestionId(allNotes.get(i).getQuestion().getId());
                syncNotes.add(notesItem);
            }
        }
        return syncNotes;
    }

    @Override
    public void onNotesSynced(NotesResponse response) {
        Log.i("Hello", "\t" + response.getNotes().size());
        mInteractor.updateNotesTable(mContext, response.getNotes());
    }

    @Override
    public void onFailure(Throwable e) {
        mView.onError(e);
    }

    public void syncUserLevel(String accessToken, UserLevels userLevels) {
        mInteractor.unpdateUserLevel(accessToken,userLevels,this);
    }

    @Override
    public void onUpdateSuccess(BaseResponse response) {
        //mView.showMessage(response.getReason());
    }
}
