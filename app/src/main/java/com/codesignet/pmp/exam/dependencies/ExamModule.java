package com.codesignet.pmp.exam.dependencies;

import com.codesignet.pmp.exam.data_access_layer.ExamInteractor;
import com.codesignet.pmp.exam.presenter.ExamPresenter;
import com.codesignet.pmp.exam.view.ExamView;

import dagger.Module;
import dagger.Provides;

@Module
public class ExamModule {

    ExamView mView;

    public ExamModule(ExamView mView) {
        this.mView = mView;
    }

    @Provides
    public ExamInteractor provideHomeINteractor() {
        return new ExamInteractor();
    }

    @Provides
    public ExamPresenter provideHomePresenter(ExamInteractor mInteractor) {
        return new ExamPresenter(mView, mInteractor);
    }
}
