package com.codesignet.pmp.exam.dependencies;


import com.codesignet.pmp.exam.ui.activity.ExamOptionActivity;
import com.codesignet.pmp.exam.ui.fragments.ExamFragment;

import dagger.Component;

@Component(modules = ExamModule.class)
public interface ExamComponent {
    void inject(ExamFragment examFragment);
    void inject(ExamOptionActivity optionFragment);
}
