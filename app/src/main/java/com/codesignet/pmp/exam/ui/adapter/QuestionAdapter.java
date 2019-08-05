package com.codesignet.pmp.exam.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codesignet.pmp.R;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionsItem;
import com.codesignet.pmp.exam.ui.view_holder.QuestionViewHolder;
import com.codesignet.pmp.exam.view.onSelectQuestion;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder> {

    private ArrayList<QuestionsItem> listData;
    private onSelectQuestion callback;
    private Context Context;
    private String Type;

    public QuestionAdapter(ArrayList<QuestionsItem> listData, Context Context, onSelectQuestion callback, String Type) {
        this.listData = listData;
        this.callback = callback;
        this.Context = Context;
        this.Type = Type;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_item, parent, false);
        return new QuestionViewHolder(itemView);
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        holder.bindView(listData.get(position), callback, position, Context, Type);
    }
}
