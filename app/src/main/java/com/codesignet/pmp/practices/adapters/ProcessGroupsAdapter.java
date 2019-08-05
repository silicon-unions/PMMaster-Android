package com.codesignet.pmp.practices.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codesignet.pmp.R;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;
import com.codesignet.pmp.practices.data_access_layer.ProcessGroupsObject;
import com.codesignet.pmp.practices.ui.view_holder.ProcessGroupsViewHolder;
import com.codesignet.pmp.practices.view.OnPracticesTypeClicked;

import java.util.ArrayList;

public class ProcessGroupsAdapter extends RecyclerView.Adapter<ProcessGroupsViewHolder> {

    private OnPracticesTypeClicked listeners;
    private ArrayList<ProcessGroupsObject> notesList;

    public ProcessGroupsAdapter(ArrayList<ProcessGroupsObject> notes, OnPracticesTypeClicked listeners) {
        this.listeners = listeners;
        this.notesList = notes;
    }

    @NonNull
    @Override
    public ProcessGroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.practices_type_item, parent, false);
        return new ProcessGroupsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessGroupsViewHolder holder, int position) {
        holder.bindView(listeners, notesList.get(position));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}