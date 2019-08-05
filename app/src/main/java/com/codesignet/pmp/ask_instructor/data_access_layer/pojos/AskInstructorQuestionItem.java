package com.codesignet.pmp.ask_instructor.data_access_layer.pojos;

import com.google.gson.annotations.SerializedName;

public class AskInstructorQuestionItem {
    @SerializedName("question")
    private String question;

    @SerializedName("answer")
    private String answer;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("id")
    private int id;

    @SerializedName("seen")
    private String seen;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

}
