package com.codesignet.pmp.exam.data_access_layer.database_manager;

public class ExamHistoryTable {
    public static final String TABLE_NAME = "exam_history";
    public static final String COLUMN_ID = "id";
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
    private String level;
    private String answerA4;
    private String justificationA;
    private String answerA3;
    private String processGroup;
    private String answerE2;
    private String answerE1;
    private String answerE4;
    private String answer;
    private String justificationE;
    private String answerE3;
    private String answerA2;
    private String answerA1;
    private String knowledgeArea;
    private String questionE;
    private String questionA;
    private String userAnswer;
    private int flag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAnswerA4() {
        return answerA4;
    }

    public void setAnswerA4(String answerA4) {
        this.answerA4 = answerA4;
    }

    public String getJustificationA() {
        return justificationA;
    }

    public void setJustificationA(String justificationA) {
        this.justificationA = justificationA;
    }

    public String getAnswerA3() {
        return answerA3;
    }

    public void setAnswerA3(String answerA3) {
        this.answerA3 = answerA3;
    }

    public String getProcessGroup() {
        return processGroup;
    }

    public void setProcessGroup(String processGroup) {
        this.processGroup = processGroup;
    }

    public String getAnswerE2() {
        return answerE2;
    }

    public void setAnswerE2(String answerE2) {
        this.answerE2 = answerE2;
    }

    public String getAnswerE1() {
        return answerE1;
    }

    public void setAnswerE1(String answerE1) {
        this.answerE1 = answerE1;
    }

    public String getAnswerE4() {
        return answerE4;
    }

    public void setAnswerE4(String answerE4) {
        this.answerE4 = answerE4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getJustificationE() {
        return justificationE;
    }

    public void setJustificationE(String justificationE) {
        this.justificationE = justificationE;
    }

    public String getAnswerE3() {
        return answerE3;
    }

    public void setAnswerE3(String answerE3) {
        this.answerE3 = answerE3;
    }

    public String getAnswerA2() {
        return answerA2;
    }

    public void setAnswerA2(String answerA2) {
        this.answerA2 = answerA2;
    }

    public String getAnswerA1() {
        return answerA1;
    }

    public void setAnswerA1(String answerA1) {
        this.answerA1 = answerA1;
    }

    public String getKnowledgeArea() {
        return knowledgeArea;
    }

    public void setKnowledgeArea(String knowledgeArea) {
        this.knowledgeArea = knowledgeArea;
    }

    public String getQuestionE() {
        return questionE;
    }

    public void setQuestionE(String questionE) {
        this.questionE = questionE;
    }

    public String getQuestionA() {
        return questionA;
    }

    public void setQuestionA(String questionA) {
        this.questionA = questionA;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}