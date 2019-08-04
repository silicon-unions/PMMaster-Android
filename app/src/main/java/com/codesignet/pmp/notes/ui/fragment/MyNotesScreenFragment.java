package com.codesignet.pmp.notes.ui.fragment;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.codesignet.pmp.R;
import com.codesignet.pmp.notes.adapters.NotesAdapter;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;
import com.codesignet.pmp.notes.view.NoteCardUtilities;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyNotesScreenFragment extends Fragment {

    @BindView(R.id.rc_my_notes)
    RecyclerView rv_notes;

    private ArrayList<Note> myNotes;
    private NotesAdapter notesAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NoteCardUtilities listeners;

    public static Fragment newInstance(ArrayList<Note> myNotes, NoteCardUtilities listeners) {
        MyNotesScreenFragment myNotesScreenFragment = new MyNotesScreenFragment();
        myNotesScreenFragment.myNotes = myNotes;
        myNotesScreenFragment.listeners = listeners;
        return myNotesScreenFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_notes_list, container, false);
        ButterKnife.bind(this, view);
        notesAdapter = new NotesAdapter(myNotes, listeners, "personalNote");
        return view;
    }

    private void InitializeRecyclerView() {
        rv_notes.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rv_notes.setLayoutManager(mLayoutManager);
        rv_notes.setAdapter(notesAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitializeRecyclerView();
    }

    @OnClick(R.id.add_new_note)
    public void onAddButtonClicked() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.view_note_layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        Note note = new Note();
        EditText noteData = dialogView.findViewById(R.id.et_message);
        dialogBuilder.setTitle(getActivity().getResources().getString(R.string.add_new_note_title));
        dialogBuilder.setPositiveButton(getActivity().getResources().getString(R.string.add), (dialog, whichButton) -> {
            note.setNote(noteData.getText().toString());
            note.setUpdatedAt(getCurrentTime());
            note.setType("personal");
            note.setId(0);
            note.setDatabaseId(null);
            listeners.onNoteAdd(note);
        });
        dialogBuilder.setNegativeButton(getActivity().getResources().getString(R.string.cancel), (dialog, whichButton) -> dialog.dismiss());
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private String getCurrentTime() {
        String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    public void onViewRefreshed(int position) {
        notesAdapter.notifyItemRemoved(position);
        notesAdapter.notifyDataSetChanged();
    }
}