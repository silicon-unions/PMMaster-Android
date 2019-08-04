package com.codesignet.pmp.practices.data_access_layer.database_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.codesignet.pmp.exam.data_access_layer.database_manager.ExamHistoryTable;
import com.codesignet.pmp.home.data_access_layer.AllStatisticsObject;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesQuestionsItem;

import java.util.ArrayList;

public class PracticesDatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 101;

    // Database Name
    private static final String DATABASE_NAME = "pmp_db";


    public PracticesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(PracticesTable.CREATE_TABLE);
        Log.i("Hello", "" + PracticesTable.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + PracticesTable.TABLE_NAME);

        // Create tables again
        onCreate(database);
    }

    public boolean addPracticesQuestions(ArrayList<PracticesQuestionsItem> questions) {
        SQLiteDatabase db = this.getWritableDatabase();

        for(PracticesQuestionsItem question : questions) {
            ContentValues values = new ContentValues();
            values.put(PracticesTable.COLUMN_QUESTION_ID, question.getId());
            values.put(PracticesTable.COLUMN_QUESTION_E, question.getQuestionE());
            values.put(PracticesTable.COLUMN_QUESTION_A, question.getQuestionA());
            values.put(PracticesTable.COLUMN_ANSWER_A1, question.getAnswerA1());
            values.put(PracticesTable.COLUMN_ANSWER_A2, question.getAnswerA2());
            values.put(PracticesTable.COLUMN_ANSWER_A3, question.getAnswerA3());
            values.put(PracticesTable.COLUMN_ANSWER_A4, question.getAnswerA4());
            values.put(PracticesTable.COLUMN_ANSWER_E1, question.getAnswerE1());
            values.put(PracticesTable.COLUMN_ANSWER_E2, question.getAnswerE2());
            values.put(PracticesTable.COLUMN_ANSWER_E3, question.getAnswerE3());
            values.put(PracticesTable.COLUMN_ANSWER_E4, question.getAnswerE4());
            values.put(PracticesTable.COLUMN_ANSWER, question.getAnswer());
            values.put(PracticesTable.COLUMN_USER_ANSWER, question.getUserAnswer());
            values.put(PracticesTable.COLUMN_KNOWLEDGE_AREA, question.getKnowledgeArea());
            values.put(PracticesTable.COLUMN_PROCESS_GROUP, question.getProcessGroup());
            values.put(PracticesTable.COLUMN_LEVEL, question.getLevel());
            values.put(PracticesTable.COLUMN_JUSTIFICATION_A, question.getJustificationA());
            values.put(PracticesTable.COLUMN_JUSTIFICATION_E, question.getJustificationE());

            // insert row
            long id = db.insert(PracticesTable.TABLE_NAME, null, values);
            if(id < 0){
                db.close();
                return false;
            }
        }
        db.close();

        // return newly inserted row id
        return true;
    }


    public PracticesQuestionsItem getQuestion(Boolean isProcessGroup, String value, String level, int previuesQuestionId) {
        // Select All Query
        String selectQuery = "SELECT * FROM " + PracticesTable.TABLE_NAME;

        if (isProcessGroup) {
            selectQuery = selectQuery.concat(" WHERE " + PracticesTable.COLUMN_PROCESS_GROUP + " = '" + value + "'");
        } else {
            selectQuery = selectQuery.concat(" WHERE " + PracticesTable.COLUMN_KNOWLEDGE_AREA + " = '" + value + "'");
        }
        selectQuery = selectQuery.concat(" AND " + PracticesTable.COLUMN_QUESTION_ID + " <> " + previuesQuestionId);
        selectQuery = selectQuery.concat(" AND NOT " + PracticesTable.COLUMN_USER_ANSWER + " = " + PracticesTable.COLUMN_ANSWER);
        selectQuery = selectQuery.concat(" AND " + PracticesTable.COLUMN_LEVEL + " =" + level + " order by RANDOM() LIMIT 1");
        //     Log.i("Hello", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        PracticesQuestionsItem question = null;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            question = new PracticesQuestionsItem();
            question.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_QUESTION_ID))));
            question.setQuestionE(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_QUESTION_E)));
            question.setQuestionA(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_QUESTION_A)));
            question.setAnswerA1(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_A1)));
            question.setAnswerA2(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_A2)));
            question.setAnswerA3(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_A3)));
            question.setAnswerA4(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_A4)));
            question.setAnswerE1(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_E1)));
            question.setAnswerE2(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_E2)));
            question.setAnswerE3(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_E3)));
            question.setAnswerE4(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_E4)));
            question.setAnswer(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER)));
            question.setUserAnswer("0");
            question.setKnowledgeArea(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_KNOWLEDGE_AREA)));
            question.setProcessGroup(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_PROCESS_GROUP)));
            question.setLevel(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_LEVEL)));
            question.setJustificationA(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_JUSTIFICATION_A)));
            question.setJustificationE(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_JUSTIFICATION_E)));
        }
        // close db connection
        db.close();

        // return notes ic_question_list
        return question;
    }

    public boolean updateUserAnswer(PracticesQuestionsItem questionsItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PracticesTable.COLUMN_USER_ANSWER, questionsItem.getUserAnswer());
        boolean value = db.update(PracticesTable.TABLE_NAME, cv,
                PracticesTable.COLUMN_QUESTION_ID + "=" + questionsItem.getId(), null) > 0;
        // close db connection
        db.close();
        return value;
    }
    public float curentQuestionNumber(Boolean isProcessGroup, String value, String level){
        float questionNumbers = 0.0f;
        String Column_name ;
        if (isProcessGroup) {
            Column_name = PracticesTable.COLUMN_PROCESS_GROUP;
        } else {
            Column_name = PracticesTable.COLUMN_KNOWLEDGE_AREA ;
        }
        String selectQuery =  "SELECT count(*) * 100/(SELECT count(*) FROM "+PracticesTable.TABLE_NAME +
                " WHERE "+Column_name+" = '"+value+"' AND level = '"+level+"')" +
                " as Percentage FROM "+PracticesTable.TABLE_NAME+" WHERE "+Column_name+" = '"+value+"' AND level = '" + level
                +"' AND " +PracticesTable.COLUMN_ANSWER+ " = " +PracticesTable.COLUMN_USER_ANSWER ;
        Log.i("Hello","curentQuestionNumber"+ selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            questionNumbers =cursor.getFloat(0);
        }
        // close db connection
        db.close();

        // return notes ic_question_list
        return questionNumbers;
    }

    public PracticesQuestionsItem getOneQuestion() {
        String selectQuery = "SELECT * FROM " + PracticesTable.TABLE_NAME + " ORDER BY RANDOM() LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        PracticesQuestionsItem question = null;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            question = new PracticesQuestionsItem();
            question.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_QUESTION_ID))));
            question.setQuestionE(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_QUESTION_E)));
            question.setQuestionA(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_QUESTION_A)));
            question.setAnswerA1(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_A1)));
            question.setAnswerA2(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_A2)));
            question.setAnswerA3(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_A3)));
            question.setAnswerA4(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_A4)));
            question.setAnswerE1(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_E1)));
            question.setAnswerE2(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_E2)));
            question.setAnswerE3(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_E3)));
            question.setAnswerE4(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER_E4)));
            question.setAnswer(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_ANSWER)));
            question.setUserAnswer("0");
            question.setKnowledgeArea(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_KNOWLEDGE_AREA)));
            question.setProcessGroup(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_PROCESS_GROUP)));
            question.setLevel(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_LEVEL)));
            question.setJustificationA(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_JUSTIFICATION_A)));
            question.setJustificationE(cursor.getString(cursor.getColumnIndex(PracticesTable.COLUMN_JUSTIFICATION_E)));
        }
        // close db connection
        db.close();

        // return notes ic_question_list
        return question;
    }

    public AllStatisticsObject getAllAllStatistics(){
        AllStatisticsObject object= new AllStatisticsObject();
        String selectQuery ="SELECT (select count(*) from "+ PracticesTable.TABLE_NAME+") " +
                "as allQuestions,(select count(*) from "+PracticesTable.TABLE_NAME+
                " where "+PracticesTable.COLUMN_ANSWER+" = "+PracticesTable.COLUMN_USER_ANSWER+") as correctAnswers,(select count(*) from "
                +PracticesTable.TABLE_NAME+" where "+PracticesTable.COLUMN_ANSWER+" != "+PracticesTable.COLUMN_USER_ANSWER
                +" AND " +PracticesTable.COLUMN_USER_ANSWER +" != '0') as wrongAnswers ";

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
    public int getPracticesCount() {
        String countQuery = "SELECT  * FROM " + PracticesTable.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}