package com.codesignet.pmp.practices.dependencies;


import com.codesignet.pmp.practices.ui.activities.PracticesActivity;
import com.codesignet.pmp.practices.ui.activities.PracticesDownloadActivity;
import com.codesignet.pmp.practices.ui.activities.PracticesLevelsActivity;

import dagger.Component;

@Component(modules = PracticesModule.class)
public interface PracticesComponent {
    void inject(PracticesActivity activity);

    void inject(PracticesLevelsActivity activity);

    void inject(PracticesDownloadActivity activity);


}
