package com.codesignet.pmp.notes.dependencies;

import com.codesignet.pmp.notes.data_access_layer.NoteInteractor;
import com.codesignet.pmp.notes.presenter.NotePresenter;
import com.codesignet.pmp.notes.view.NoteView;

import dagger.Module;
import dagger.Provides;

@Module
public class NotesModule {
    NoteView mView;

    public NotesModule(NoteView mView) {
        this.mView = mView;
    }

    @Provides
    public NoteInteractor provideNoteInteractor() {
        return new NoteInteractor();
    }

    @Provides
    public NotePresenter provideNotePresenter(NoteInteractor mInteractor) {
        return new NotePresenter(mView, mInteractor);
    }
}
