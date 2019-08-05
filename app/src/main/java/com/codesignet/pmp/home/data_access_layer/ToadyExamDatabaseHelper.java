package com.codesignet.pmp.home.data_access_layer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codesignet.pmp.exam.data_access_layer.database_manager.ExamHistoryTable;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionsItem;

import java.util.ArrayList;

public class ToadyExamDatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 101;

    // Database Name
    private static final String DATABASE_NAME = "pmp_db";


    public ToadyExamDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(TodayExamTable.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TodayExamTable.TABLE_NAME);
        onCreate(db);
    }

    public TodayExamObject getTodayExamData(String currentDate) {
        String selectQuery = "SELECT * FROM " + TodayExamTable.TABLE_NAME + " WHERE " + TodayExamTable.COLUMN_EXAM_DATE + " = '" + currentDate + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        TodayExamObject todayExamObject = null;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            todayExamObject = new TodayExamObject();
            todayExamObject.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TodayExamTable.COLUMN_ID))));
            todayExamObject.setCorrectAnswer(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TodayExamTable.COLUMN_CORRECT))));
            todayExamObject.setWrongAnswer(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TodayExamTable.COLUMN_WRONG))));
            todayExamObject.setExamDate(cursor.getString(cursor.getColumnIndex(TodayExamTable.COLUMN_EXAM_DATE)));
        }
        db.close();
        return todayExamObject;
    }

    public void updateTodayExamData(String currentDate, int correct, int wrong) {
        boolean value;
        TodayExamObject todayExamObject = getTodayExamData(currentDate);
        if (todayExamObject != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TodayExamTable.COLUMN_CORRECT, todayExamObject.getCorrectAnswer() + correct);
            cv.put(TodayExamTable.COLUMN_WRONG, todayExamObject.getWrongAnswer() + wrong);
            value = db.update(TodayExamTable.TABLE_NAME, cv, TodayExamTable.COLUMN_ID + "=" + todayExamObject.getId(), null) > 0;
            db.close();
        } else {
            addTodayExamData(CreateTodayExamData(currentDate, correct, wrong));
        }
    }

    public ArrayList<TodayExamObject> getTodayExamHistory() {
        String selectQuery = "SELECT * FROM " + TodayExamTable.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        TodayExamObject todayExamObject = null;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to ic_question_list
        ArrayList<TodayExamObject> history = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                todayExamObject = new TodayExamObject();
                todayExamObject.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TodayExamTable.COLUMN_ID))));
                todayExamObject.setCorrectAnswer(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TodayExamTable.COLUMN_CORRECT))));
                todayExamObject.setWrongAnswer(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TodayExamTable.COLUMN_WRONG))));
                todayExamObject.setExamDate(cursor.getString(cursor.getColumnIndex(TodayExamTable.COLUMN_EXAM_DATE)));
                history.add(todayExamObject);
            } while (cursor.moveToNext());
        }
        db.close();
        return history;
    }

    public boolean addTodayExamData(TodayExamObject todayExamObject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TodayExamTable.COLUMN_CORRECT, todayExamObject.getCorrectAnswer());
        values.put(TodayExamTable.COLUMN_WRONG, todayExamObject.getWrongAnswer());
        values.put(TodayExamTable.COLUMN_EXAM_DATE, todayExamObject.getExamDate());

        // insert row
        long id = db.insert(TodayExamTable.TABLE_NAME, null, values);
        if (id < 0) {
            db.close();
            return false;
        }
        db.close();

        // return newly inserted row id
        return true;
    }

    private TodayExamObject CreateTodayExamData(String currentDate, int correct, int wrong) {
        TodayExamObject todayExamObject = new TodayExamObject();
        todayExamObject.setCorrectAnswer(correct);
        todayExamObject.setWrongAnswer(wrong);
        todayExamObject.setExamDate(currentDate);
        return todayExamObject;
    }
}
