package com.codesignet.pmp.Authentication.dependencies;

import com.codesignet.pmp.Authentication.data_access_layer.AuthenticationInteractor;
import com.codesignet.pmp.Authentication.presenter.AuthenticationPresenter;
import com.codesignet.pmp.Authentication.view.AuthenticationView;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthenticationModule {
    AuthenticationView mView;

    public AuthenticationModule(AuthenticationView mView) {
        this.mView = mView;
    }

    @Provides
    public AuthenticationInteractor provideAuthenticationINteractor() {
        return new AuthenticationInteractor();
    }

    @Provides
    public AuthenticationPresenter provideAuthenticationPresenter(AuthenticationInteractor mInteractor) {
        return new AuthenticationPresenter(mView, mInteractor);
    }

}
