package com.codesignet.pmp.notes.data_access_layer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codesignet.pmp.app.Constants;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;
import com.codesignet.pmp.notes.data_access_layer.pojos.Question;

import java.util.ArrayList;
import java.util.List;

public class NotesDatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 101;

    // Database Name
    private static final String DATABASE_NAME = "pmp_db";


    public NotesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(PersonalNoteTable.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + PersonalNoteTable.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PersonalNoteTable.COLUMN_NOTE, note.getNote());
        values.put(PersonalNoteTable.COLUMN_NOTE_ID, String.valueOf(note.getId()));
        values.put(PersonalNoteTable.COLUMN_NOTE_TYPE, note.getType());
        values.put(PersonalNoteTable.COLUMN_NOTE_DATE, note.getUpdatedAt());
        // insert row
        long id = db.insert(PersonalNoteTable.TABLE_NAME, null, values);
        db.close();

        // return newly inserted row id
        return id;
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + PersonalNoteTable.TABLE_NAME + " WHERE " +
                PersonalNoteTable.COLUMN_NOTE_DELETED + " = 0" + " ORDER BY " +
                PersonalNoteTable.COLUMN_NOTE_DATE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to ic_question_list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setDatabaseId((cursor.getInt(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ID))));
                note.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_NOTE_ID))));
                note.setType(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_NOTE_TYPE)));
                note.setNote(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_NOTE)));
                note.setUpdatedAt(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_NOTE_DATE)));

                Question question = new Question();

                question.setAnswer(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER)));
                question.setAnswerA1(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_A1)));
                question.setAnswerA2(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_A2)));
                question.setAnswerA3(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_A3)));
                question.setAnswerA4(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_A4)));

                question.setAnswerE1(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_E1)));
                question.setAnswerE2(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_E2)));
                question.setAnswerE3(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_E3)));
                question.setAnswerE4(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_E4)));

                question.setId(cursor.getInt(cursor.getColumnIndex(PersonalNoteTable.COLUMN_QUESTION_ID)));
                question.setLevel(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_LEVEL)));

                question.setJustificationA(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_JUSTIFICATION_A)));
                question.setJustificationE(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_JUSTIFICATION_E)));

                question.setQuestionA(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_QUESTION_A)));
                question.setQuestionE(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_QUESTION_E)));

                question.setProcessGroup(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_PROCESS_GROUP)));
                question.setKnowledgeArea(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_KNOWLEDGE_AREA)));
                note.setQuestion(question);

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes ic_question_list
        return notes;
    }

    public ArrayList<Note> getAllSyncNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + PersonalNoteTable.TABLE_NAME + " ORDER BY " + PersonalNoteTable.COLUMN_NOTE_DATE + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to ic_question_list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setDatabaseId((cursor.getInt(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ID))));
                note.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_NOTE_ID))));
                note.setType(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_NOTE_TYPE)));
                note.setNote(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_NOTE)));
                note.setUpdatedAt(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_NOTE_DATE)));
                note.setIsdeleted((cursor.getInt(cursor.getColumnIndex(PersonalNoteTable.COLUMN_NOTE_DELETED))));

                Question question = new Question();

                question.setAnswer(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER)));
                question.setAnswerA1(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_A1)));
                question.setAnswerA2(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_A2)));
                question.setAnswerA3(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_A3)));
                question.setAnswerA4(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_A4)));

                question.setAnswerE1(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_E1)));
                question.setAnswerE2(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_E2)));
                question.setAnswerE3(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_E3)));
                question.setAnswerE4(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_ANSWER_E4)));

                question.setId(cursor.getInt(cursor.getColumnIndex(PersonalNoteTable.COLUMN_QUESTION_ID)));
                question.setLevel(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_LEVEL)));

                question.setJustificationA(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_JUSTIFICATION_A)));
                question.setJustificationE(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_JUSTIFICATION_E)));

                question.setQuestionA(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_QUESTION_A)));
                question.setQuestionE(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_QUESTION_E)));

                question.setProcessGroup(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_PROCESS_GROUP)));
                question.setKnowledgeArea(cursor.getString(cursor.getColumnIndex(PersonalNoteTable.COLUMN_KNOWLEDGE_AREA)));
                note.setQuestion(question);

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes ic_question_list
        return notes;
    }

    //---deletes a particular title---
    public boolean deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PersonalNoteTable.COLUMN_NOTE_DELETED, 1);
        boolean value = db.update(PersonalNoteTable.TABLE_NAME, cv, PersonalNoteTable.COLUMN_ID + "=" + note.getDatabaseId(), null) > 0;
        // close db connection
        db.close();
        return value;
    }

    public boolean updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PersonalNoteTable.COLUMN_NOTE, note.getNote());
        cv.put(PersonalNoteTable.COLUMN_NOTE_DATE, note.getUpdatedAt());
        boolean value = db.update(PersonalNoteTable.TABLE_NAME, cv,
                PersonalNoteTable.COLUMN_ID + "=" + note.getDatabaseId(), null) > 0;
        // close db connection
        db.close();
        return value;
    }

    public long insertNoteWithQuestion(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PersonalNoteTable.COLUMN_NOTE, note.getNote());
        values.put(PersonalNoteTable.COLUMN_NOTE_ID, String.valueOf(note.getId()));
        values.put(PersonalNoteTable.COLUMN_NOTE_TYPE, note.getType());
        values.put(PersonalNoteTable.COLUMN_NOTE_DATE, note.getUpdatedAt());

        values.put(PersonalNoteTable.COLUMN_ANSWER, note.getQuestion().getAnswer());
        values.put(PersonalNoteTable.COLUMN_ANSWER_A1, note.getQuestion().getAnswerA1());
        values.put(PersonalNoteTable.COLUMN_ANSWER_A2, note.getQuestion().getAnswerA2());
        values.put(PersonalNoteTable.COLUMN_ANSWER_A3, note.getQuestion().getAnswerA3());
        values.put(PersonalNoteTable.COLUMN_ANSWER_A4, note.getQuestion().getAnswerA4());

        values.put(PersonalNoteTable.COLUMN_ANSWER_E1, note.getQuestion().getAnswerE1());
        values.put(PersonalNoteTable.COLUMN_ANSWER_E2, note.getQuestion().getAnswerE2());
        values.put(PersonalNoteTable.COLUMN_ANSWER_E3, note.getQuestion().getAnswerE3());
        values.put(PersonalNoteTable.COLUMN_ANSWER_E4, note.getQuestion().getAnswerE4());

        values.put(PersonalNoteTable.COLUMN_QUESTION_ID, note.getQuestion().getId());
        values.put(PersonalNoteTable.COLUMN_LEVEL, note.getQuestion().getLevel());

        values.put(PersonalNoteTable.COLUMN_JUSTIFICATION_A, note.getQuestion().getJustificationA());
        values.put(PersonalNoteTable.COLUMN_JUSTIFICATION_E, note.getQuestion().getJustificationE());

        values.put(PersonalNoteTable.COLUMN_QUESTION_A, note.getQuestion().getQuestionA());
        values.put(PersonalNoteTable.COLUMN_QUESTION_E, note.getQuestion().getQuestionE());

        values.put(PersonalNoteTable.COLUMN_PROCESS_GROUP, note.getQuestion().getProcessGroup());
        values.put(PersonalNoteTable.COLUMN_KNOWLEDGE_AREA, note.getQuestion().getKnowledgeArea());

        // insert row
        long id = db.insert(PersonalNoteTable.TABLE_NAME, null, values);
        db.close();

        // return newly inserted row id
        return id;
    }

    public void updateNotesTable(List<Note> notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + PersonalNoteTable.TABLE_NAME);
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getType().equals(Constants.QUESTION_TYPE))
                insertNoteWithQuestion(notes.get(i));
            else
                insertNote(notes.get(i));
        }
    }
}