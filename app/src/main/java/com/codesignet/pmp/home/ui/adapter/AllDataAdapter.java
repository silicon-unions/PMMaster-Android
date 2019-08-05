package com.codesignet.pmp.home.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codesignet.pmp.R;
import com.codesignet.pmp.home.ui.AllDataViewHolder;
import com.codesignet.pmp.home.ui.AllRecordValues;
import com.codesignet.pmp.practices.data_access_layer.ProcessGroupsObject;

import java.util.ArrayList;

public class AllDataAdapter extends RecyclerView.Adapter<AllDataViewHolder> {
    private ArrayList<AllRecordValues> notesList;
    public AllDataAdapter(ArrayList<AllRecordValues> notes) {
        this.notesList = notes;
    }

    @NonNull
    @Override
    public AllDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.practices_type_item, parent, false);
        return new AllDataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllDataViewHolder holder, int position) {
        holder.bindView(notesList.get(position));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}