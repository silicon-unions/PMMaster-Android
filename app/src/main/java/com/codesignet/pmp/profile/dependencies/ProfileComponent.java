package com.codesignet.pmp.profile.dependencies;

import com.codesignet.pmp.profile.ui.activity.ProfileActivityScreen;

import dagger.Component;

@Component(modules = ProfileModule.class)
public interface ProfileComponent {
    void inject(ProfileActivityScreen profileActivityScreen);
}
