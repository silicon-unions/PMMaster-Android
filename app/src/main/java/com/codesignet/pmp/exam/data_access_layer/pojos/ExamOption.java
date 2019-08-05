package com.codesignet.pmp.exam.data_access_layer.pojos;

import java.io.Serializable;

public class ExamOption implements Serializable {
    // in-case of true will be exam type --------- false question type
    private boolean timerType;
    private long examCalendar;
    private long questionCalendar;
    private int questionNumber;

    public boolean isTimerType() {
        return timerType;
    }

    public void setTimerType(boolean timerType) {
        this.timerType = timerType;
    }

    public long getExamCalendar() {
        return examCalendar;
    }

    public void setExamCalendar(long examCalendar) {
        this.examCalendar = examCalendar;
    }

    public long getQuestionCalendar() {
        return questionCalendar;
    }

    public void setQuestionCalendar(long questionCalendar) {
        this.questionCalendar = questionCalendar;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }
}
