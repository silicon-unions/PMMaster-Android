package com.codesignet.pmp.ask_instructor.dependencies;

import com.codesignet.pmp.ask_instructor.data_access_layer.AskInteractor;
import com.codesignet.pmp.ask_instructor.presenter.AskPresenter;
import com.codesignet.pmp.ask_instructor.view.AskView;

import dagger.Module;
import dagger.Provides;

@Module
public class AskModule {
    AskView mView;

    public AskModule(AskView mView) {
        this.mView = mView;
    }

    @Provides
    public AskInteractor provideNoteInteractor() {
        return new AskInteractor();
    }

    @Provides
    public AskPresenter provideNotePresenter(AskInteractor mInteractor) {
        return new AskPresenter(mView, mInteractor);
    }
}
