package com.codesignet.pmp.notes.data_access_layer.database;

public class QuestionNoteTable {
    public static final String TABLE_NAME = "Personal_notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE_ID = "noteId";
    public static final String COLUMN_NOTE_TYPE = "type";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_NOTE_DATE = "note_date";
    public static final String COLUMN_QUESTION_ID = "questionId";
    public static final String COLUMN_QUESTION_E = "questionE";
    public static final String COLUMN_QUESTION_A = "questionA";
    public static final String COLUMN_ANSWER_E1 = "answerE1";
    public static final String COLUMN_ANSWER_E2 = "answerE2";
    public static final String COLUMN_ANSWER_E3 = "answerE3";
    public static final String COLUMN_ANSWER_E4 = "answerE4";
    public static final String COLUMN_ANSWER_A1 = "answerA1";
    public static final String COLUMN_ANSWER_A2 = "answerA2";
    public static final String COLUMN_ANSWER_A3 = "answerA3";
    public static final String COLUMN_ANSWER_A4 = "answerA4";
    public static final String COLUMN_ANSWER = "answer";
    public static final String COLUMN_USER_ANSWER = "userAnswer";
    public static final String COLUMN_PROCESS_GROUP = "processGroup";
    public static final String COLUMN_KNOWLEDGE_AREA = "knowledgeArea";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_JUSTIFICATION_E = "justificationE";
    public static final String COLUMN_JUSTIFICATION_A = "justificationA";
    public static final String COLUMN_FLAG = "flag";
    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOTE_ID + " INTEGER,"
                    + COLUMN_NOTE_TYPE + " TEXT,"
                    + COLUMN_NOTE_DATE + " TEXT,"
                    + COLUMN_NOTE + " TEXT,"
                    + COLUMN_QUESTION_ID + " INTEGER,"
                    + COLUMN_QUESTION_E + " TEXT,"
                    + COLUMN_QUESTION_A + " TEXT,"
                    + COLUMN_ANSWER_E1 + " TEXT,"
                    + COLUMN_ANSWER_E2 + " TEXT,"
                    + COLUMN_ANSWER_E3 + " TEXT,"
                    + COLUMN_ANSWER_E4 + " TEXT,"
                    + COLUMN_ANSWER_A1 + " TEXT,"
                    + COLUMN_ANSWER_A2 + " TEXT,"
                    + COLUMN_ANSWER_A3 + " TEXT,"
                    + COLUMN_ANSWER_A4 + " TEXT,"
                    + COLUMN_ANSWER + " TEXT,"
                    + COLUMN_USER_ANSWER + " TEXT,"
                    + COLUMN_PROCESS_GROUP + " TEXT,"
                    + COLUMN_KNOWLEDGE_AREA + " TEXT,"
                    + COLUMN_LEVEL + " TEXT,"
                    + COLUMN_JUSTIFICATION_E + " TEXT,"
                    + COLUMN_FLAG + " INTEGER DEFAULT 0,"
                    + COLUMN_JUSTIFICATION_A + " TEXT"
                    + ")";
    private int id;
    private String note;
    private String noteId;
    private String type;
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}