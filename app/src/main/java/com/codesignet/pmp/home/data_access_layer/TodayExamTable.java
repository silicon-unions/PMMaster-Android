package com.codesignet.pmp.home.data_access_layer;

public class TodayExamTable {
    public static final String TABLE_NAME = "today_exam_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CORRECT = "correct_count";
    public static final String COLUMN_WRONG = "wrong_count";
    public static final String COLUMN_EXAM_DATE ="exam_date";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CORRECT + " INTEGER,"
                    + COLUMN_WRONG + " INTEGER,"
                    + COLUMN_EXAM_DATE + " TEXT"
                    + ")";
    private int id;
    private int correct;
    private int wrong;
    private String examDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }
}
