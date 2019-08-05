package com.codesignet.pmp.exam.data_access_layer.database_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionsItem;
import com.codesignet.pmp.home.data_access_layer.AllStatisticsObject;

import java.util.ArrayList;

public class ExamDatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 101;

    // Database Name
    private static final String DATABASE_NAME = "pmp_db";


    public ExamDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(ExamHistoryTable.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ExamHistoryTable.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long addWrongQuestion(QuestionsItem question) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ExamHistoryTable.COLUMN_QUESTION_ID, question.getId());
        values.put(ExamHistoryTable.COLUMN_QUESTION_E, question.getQuestionE());
        values.put(ExamHistoryTable.COLUMN_QUESTION_A, question.getQuestionA());
        values.put(ExamHistoryTable.COLUMN_ANSWER_A1, question.getAnswerA1());
        values.put(ExamHistoryTable.COLUMN_ANSWER_A2, question.getAnswerA2());
        values.put(ExamHistoryTable.COLUMN_ANSWER_A3, question.getAnswerA3());
        values.put(ExamHistoryTable.COLUMN_ANSWER_A4, question.getAnswerA4());
        values.put(ExamHistoryTable.COLUMN_ANSWER_E1, question.getAnswerE1());
        values.put(ExamHistoryTable.COLUMN_ANSWER_E2, question.getAnswerE2());
        values.put(ExamHistoryTable.COLUMN_ANSWER_E3, question.getAnswerE3());
        values.put(ExamHistoryTable.COLUMN_ANSWER_E4, question.getAnswerE4());
        values.put(ExamHistoryTable.COLUMN_ANSWER, question.getAnswer());
        values.put(ExamHistoryTable.COLUMN_USER_ANSWER, question.getUserAnswer());
        values.put(ExamHistoryTable.COLUMN_KNOWLEDGE_AREA, question.getKnowledgeArea());
        values.put(ExamHistoryTable.COLUMN_PROCESS_GROUP, question.getProcessGroup());
        values.put(ExamHistoryTable.COLUMN_LEVEL, question.getLevel());
        values.put(ExamHistoryTable.COLUMN_JUSTIFICATION_A, question.getJustificationA());
        values.put(ExamHistoryTable.COLUMN_JUSTIFICATION_E, question.getJustificationE());
        values.put(ExamHistoryTable.COLUMN_FLAG, question.isFlag());

        // insert row
        long id = db.insert(ExamHistoryTable.TABLE_NAME, null, values);
        db.close();

        // return newly inserted row id
        return id;
    }

    public ArrayList<QuestionsItem> getAllQuestions() {
        ArrayList<QuestionsItem> questions = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ExamHistoryTable.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to ic_question_list
        if (cursor.moveToFirst()) {
            do {
                QuestionsItem question = new QuestionsItem();
                question.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_QUESTION_ID))));
                question.setQuestionE(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_QUESTION_E)));
                question.setQuestionA(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_QUESTION_A)));
                question.setAnswerA1(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_ANSWER_A1)));
                question.setAnswerA2(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_ANSWER_A2)));
                question.setAnswerA3(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_ANSWER_A3)));
                question.setAnswerA4(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_ANSWER_A4)));
                question.setAnswerE1(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_ANSWER_E1)));
                question.setAnswerE2(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_ANSWER_E2)));
                question.setAnswerE3(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_ANSWER_E3)));
                question.setAnswerE4(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_ANSWER_E4)));
                question.setAnswer(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_ANSWER)));
                question.setUserAnswer(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_USER_ANSWER)));
                question.setKnowledgeArea(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_KNOWLEDGE_AREA)));
                question.setProcessGroup(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_PROCESS_GROUP)));
                question.setLevel(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_LEVEL)));
                question.setJustificationA(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_JUSTIFICATION_A)));
                question.setJustificationE(cursor.getString(cursor.getColumnIndex(ExamHistoryTable.COLUMN_JUSTIFICATION_E)));
                question.setFlag(cursor.getInt(cursor.getColumnIndex(ExamHistoryTable.COLUMN_FLAG)) == 1);

                questions.add(question);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes ic_question_list
        return questions;
    }

    public AllStatisticsObject getAllAllStatistics(){
        AllStatisticsObject object= new AllStatisticsObject();
        String selectQuery ="SELECT (select count(*) from "+ExamHistoryTable.TABLE_NAME+") " +
                "as allQuestions,(select count(*) from "+ExamHistoryTable.TABLE_NAME+
                " where "+ExamHistoryTable.COLUMN_ANSWER+" = "+ExamHistoryTable.COLUMN_USER_ANSWER+") as correctAnswers,(select count(*) from "
                +ExamHistoryTable.TABLE_NAME+" where "+ExamHistoryTable.COLUMN_ANSWER+" != "+ExamHistoryTable.COLUMN_USER_ANSWER
                +") as wrongAnswers";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to ic_question_list
        if (cursor.moveToFirst()) {
            object.setWrongAnswers(Integer.parseInt(cursor.getString(cursor.getColumnIndex("wrongAnswers"))));
            object.setCorrectAnswers(Integer.parseInt(cursor.getString(cursor.getColumnIndex("correctAnswers"))));
            object.setAllQuestions(Integer.parseInt(cursor.getString(cursor.getColumnIndex("allQuestions"))));

            object.setUnresolvedAnswers(object.getAllQuestions()-(object.getCorrectAnswers()+object.getWrongAnswers()));
        }

        // close db connection
        db.close();

        // return notes ic_question_list
        return object;
    }
}