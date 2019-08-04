package com.codesignet.pmp.home.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codesignet.pmp.R;
import com.codesignet.pmp.home.data_access_layer.TodayExamObject;
import com.codesignet.pmp.home.ui.view_holder.TodayExamDataViewHolder;

import java.util.ArrayList;

public class TodayExamDataAdapter extends RecyclerView.Adapter<TodayExamDataViewHolder> {

    private ArrayList<TodayExamObject> todayExamDataList;

    public TodayExamDataAdapter(ArrayList<TodayExamObject> list) {
        this.todayExamDataList = list;
    }

    @NonNull
    @Override
    public TodayExamDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.today_exam_date_layout, parent, false);
        return new TodayExamDataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayExamDataViewHolder holder, int position) {
        holder.bindView(todayExamDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return todayExamDataList.size();
    }
}