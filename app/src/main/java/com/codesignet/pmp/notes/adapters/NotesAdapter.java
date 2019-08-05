package com.codesignet.pmp.notes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codesignet.pmp.R;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;
import com.codesignet.pmp.notes.ui.NotesViewHolder;
import com.codesignet.pmp.notes.view.NoteCardUtilities;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    private NoteCardUtilities listeners;
    private ArrayList<Note> notesList;
    private String type;

    public NotesAdapter(ArrayList<Note> notes, NoteCardUtilities listeners, String type) {
        this.listeners = listeners;
        this.notesList = notes;
        this.type = type;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.bindView(listeners, notesList.get(position), position, type);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
