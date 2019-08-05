package com.codesignet.pmp.notes.data_access_layer;

import android.content.Context;

import com.codesignet.pmp.basics.BaseInteractour;
import com.codesignet.pmp.notes.data_access_layer.database.NotesDatabaseHelper;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;

import java.util.ArrayList;

public class NoteInteractor extends BaseInteractour {

    private NotesDatabaseHelper databaseHelper;


    public ArrayList<Note> getAllNotes(Context mContext) {
        databaseHelper = new NotesDatabaseHelper(mContext);
        return databaseHelper.getAllNotes();
    }

    public boolean deleteNote(Note note, Context mContext) {
        databaseHelper = new NotesDatabaseHelper(mContext);
        return databaseHelper.deleteNote(note);

    }

    public boolean updateCurrentNote(Note note, Context mContext) {
        databaseHelper = new NotesDatabaseHelper(mContext);
        return databaseHelper.updateNote(note);
    }

    public long addNewNote(Note note, Context mContext) {
        databaseHelper = new NotesDatabaseHelper(mContext);
        return databaseHelper.insertNote(note);
    }
}
