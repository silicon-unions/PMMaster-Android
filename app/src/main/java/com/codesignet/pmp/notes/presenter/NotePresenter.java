package com.codesignet.pmp.notes.presenter;

import android.content.Context;
import android.util.Log;

import com.codesignet.pmp.basics.BasePresenter;
import com.codesignet.pmp.notes.data_access_layer.NoteInteractor;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;
import com.codesignet.pmp.notes.view.NoteView;

public class NotePresenter extends BasePresenter<NoteView, NoteInteractor> {

    private NoteInteractor mInteractor;
    private NoteView mView;

    public NotePresenter(NoteView mView, NoteInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    public void getAllNotesFromAPI(Context mContext) {
        mView.showLoader();
        if (mInteractor.getAllNotes(mContext) != null) {
            mView.hideLoader();
            mView.showAllNotes(mInteractor.getAllNotes(mContext));
        } else {
            mView.showMessage("There is no notes");
        }
    }

    public void addNewNote(Note note, Context mContext) {
        mView.showLoader();
        if (mInteractor.addNewNote(note, mContext) != -1) {
            mView.showMessage("Done");
            mView.refresh();
        } else {
            mView.showMessage("Something went wrong");
        }
    }

    public void updateNote(Note note, Context mContext) {
        mView.showLoader();
        if (mInteractor.updateCurrentNote(note, mContext)) {
            mView.hideLoader();
            mView.refresh();
        } else {
            mView.hideLoader();
            Log.i("Hello", "something wrong");
        }
    }

    public void deleteNote(Note note, Context mContext) {
        mView.showLoader();
        if (mInteractor.deleteNote(note, mContext)) {
            mView.hideLoader();
            mView.refresh();
        }
    }
}
