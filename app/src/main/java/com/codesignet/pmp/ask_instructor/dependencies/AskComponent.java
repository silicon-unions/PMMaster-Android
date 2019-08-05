package com.codesignet.pmp.ask_instructor.dependencies;

import com.codesignet.pmp.ask_instructor.ui.activity.AskScreenActivity;

import dagger.Component;

@Component(modules = AskModule.class)
public interface AskComponent {
    void inject(AskScreenActivity activity);
}
