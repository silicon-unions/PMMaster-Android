package com.codesignet.pmp.profile.dependencies;

import com.codesignet.pmp.profile.data_access_layer.ProfileInteractor;
import com.codesignet.pmp.profile.presenter.ProfilePresenter;
import com.codesignet.pmp.profile.view.ProfileView;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfileModule {
    ProfileView mView;

    public ProfileModule(ProfileView mView) {
        this.mView = mView;
    }

    @Provides
    public ProfileInteractor provideNoteInteractor() {
        return new ProfileInteractor();
    }

    @Provides
    public ProfilePresenter provideNotePresenter(ProfileInteractor mInteractor) {
        return new ProfilePresenter(mView, mInteractor);
    }
}
