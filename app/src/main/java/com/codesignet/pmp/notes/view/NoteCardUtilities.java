package com.codesignet.pmp.notes.view;

import com.codesignet.pmp.notes.data_access_layer.pojos.Note;

public interface NoteCardUtilities {
    void onDeleteNote(Note id, int position, String type);
    void onNoteClicked(Note note);
    void onNoteAdd(Note note);
}
