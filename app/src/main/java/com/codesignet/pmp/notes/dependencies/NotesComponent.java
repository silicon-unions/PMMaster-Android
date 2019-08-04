package com.codesignet.pmp.notes.dependencies;

import com.codesignet.pmp.notes.ui.activity.NotesScreenActivity;
import com.codesignet.pmp.notes.ui.fragment.MyNotesScreenFragment;
import com.codesignet.pmp.notes.ui.fragment.QuestionNotesScreen;

import dagger.Component;

@Component(modules = NotesModule.class)
public interface NotesComponent {
    void inject(NotesScreenActivity notesScreenActivity);

    void inject(MyNotesScreenFragment myNotesScreenFragment);

    void inject(QuestionNotesScreen questionNotesScreen);
}
