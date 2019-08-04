package com.codesignet.pmp.practices.dependencies;

import com.codesignet.pmp.practices.data_access_layer.PracticesInteractor;
import com.codesignet.pmp.practices.presenter.PracticesPresenter;
import com.codesignet.pmp.practices.view.PracticesView;

import dagger.Module;
import dagger.Provides;

@Module
public class PracticesModule {

    private PracticesView mView;

    public PracticesModule(PracticesView mView) {
        this.mView = mView;
    }

    @Provides
    public PracticesInteractor providePracticesInteractor() {
        return new PracticesInteractor();
    }

    @Provides
    public PracticesPresenter providePracticesPresenter(PracticesInteractor mInteractor) {
        return new PracticesPresenter(mView, mInteractor);
    }
}
