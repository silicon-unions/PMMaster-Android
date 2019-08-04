package com.codesignet.pmp.home.dependencies;

import com.codesignet.pmp.home.data_access_layer.HomeInteractor;
import com.codesignet.pmp.home.presenter.HomePresenter;
import com.codesignet.pmp.home.view.HomeView;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    HomeView mView;

    public HomeModule(HomeView mView) {
        this.mView = mView;
    }

    @Provides
    public HomeInteractor provideHomeINteractor() {
        return new HomeInteractor();
    }

    @Provides
    public HomePresenter provideHomePresenter(HomeInteractor mInteractor) {
        return new HomePresenter(mView, mInteractor);
    }
}
