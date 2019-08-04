package com.codesignet.pmp.notes.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codesignet.pmp.R;
import com.codesignet.pmp.notes.adapters.NotesAdapter;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;
import com.codesignet.pmp.notes.view.NoteCardUtilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionNotesScreen extends Fragment {

    @BindView(R.id.rc_my_notes)
    RecyclerView rv_notes;
    private ArrayList<Note> myNotes;
    private NotesAdapter notesAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NoteCardUtilities listeners;

    public static Fragment newInstance(ArrayList<Note> questionNotes, NoteCardUtilities listeners) {
        QuestionNotesScreen questionNotesScreen = new QuestionNotesScreen();
        questionNotesScreen.myNotes = questionNotes;
        questionNotesScreen.listeners = listeners;
        return questionNotesScreen;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_notes_list, container, false);
        ButterKnife.bind(this, view);
        notesAdapter = new NotesAdapter(myNotes, listeners, "questionNotes");
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

    public void onViewRefreshed(int position) {
        notesAdapter.notifyItemRemoved(position);
        notesAdapter.notifyDataSetChanged();
    }
}
