package com.codesignet.pmp.notes.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codesignet.pmp.R;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;
import com.codesignet.pmp.notes.view.NoteCardUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotesViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.note_title)
    TextView noteTitle;

    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.iv_delete_note)
    ImageView iv_delete_note;

    private View card;
    private NoteCardUtilities listeners;
    private Note note;
    private int position;
    private String type;

    public NotesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        card = itemView;
    }

    public void bindView(NoteCardUtilities listeners, Note note, int position, String type) {
        this.listeners = listeners;
        this.note = note;
        this.position = position;
        this.type = type;
        card.setOnClickListener((View v) -> listeners.onNoteClicked(note));
        iv_delete_note.setOnClickListener((View v) -> {
            listeners.onDeleteNote(note, position, type);
        });
        noteTitle.setText(note.getNote());
        time.setText(note.getUpdatedAt());
    }

    @OnClick(R.id.iv_delete_note)
    public void onDeleteClicked() {
        listeners.onDeleteNote(note, position, type);
    }
}
