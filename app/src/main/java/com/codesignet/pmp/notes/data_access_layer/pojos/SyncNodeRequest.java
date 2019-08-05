package com.codesignet.pmp.notes.data_access_layer.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SyncNodeRequest {

    @SerializedName("notes")
    private List<NotesItem> notes;

    public List<NotesItem> getNotes() {
        return notes;
    }

    public void setNotes(List<NotesItem> notes) {
        this.notes = notes;
    }
}