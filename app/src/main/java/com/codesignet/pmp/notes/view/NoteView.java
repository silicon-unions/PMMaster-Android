package com.codesignet.pmp.notes.view;

import com.codesignet.pmp.basics.BaseView;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;

import java.util.List;

public interface NoteView extends BaseView {
    void showAllNotes(List<Note> notes);

    void showLoader();

    void hideLoader();

    void refresh();
}