package com.codesignet.pmp.home.dependencies;


import com.codesignet.pmp.home.ui.activity.HomeActivity;

import dagger.Component;

@Component(modules = HomeModule.class)
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
}
