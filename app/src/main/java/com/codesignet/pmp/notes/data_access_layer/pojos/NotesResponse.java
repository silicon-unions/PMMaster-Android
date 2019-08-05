package com.codesignet.pmp.notes.data_access_layer.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotesResponse {

    @SerializedName("notes")
    private List<Note> notes;

    @SerializedName("success")
    private boolean success;

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}