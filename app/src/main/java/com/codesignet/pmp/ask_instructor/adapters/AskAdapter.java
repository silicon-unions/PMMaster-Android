package com.codesignet.pmp.ask_instructor.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codesignet.pmp.R;
import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.AskInstructorQuestionItem;
import com.codesignet.pmp.ask_instructor.ui.AskViewHolder;
import com.codesignet.pmp.ask_instructor.view.AskView;

import java.util.ArrayList;

public class AskAdapter extends RecyclerView.Adapter<AskViewHolder> {

    private AskView listeners;
    private ArrayList<AskInstructorQuestionItem> questionsList;

    public AskAdapter(AskView listeners, ArrayList<AskInstructorQuestionItem> questionsList) {
        this.listeners = listeners;
        this.questionsList = questionsList;
    }

    @NonNull
    @Override
    public AskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ask_item, parent, false);
        return new AskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AskViewHolder holder, int position) {
        holder.time.setText(questionsList.get(position).getUpdatedAt());
        holder.delete_question.setOnClickListener(v -> listeners.onDeleteQuestion(questionsList.get(position).getId(), position));
        holder.question.setText(questionsList.get(position).getQuestion());
        holder.itemView.setOnClickListener(v -> listeners.showAskedQuestionDialog(questionsList.get(position)));
        if (questionsList.get(position).getAnswer() != null && questionsList.get(position).getSeen().equals("0")) {
            holder.status.setText("Answered");
            holder.new_status.setVisibility(View.VISIBLE);
        } else if (questionsList.get(position).getAnswer() != null && questionsList.get(position).getSeen().equals("1")) {
            holder.status.setText("Answered");
            holder.new_status.setVisibility(View.GONE);
        } else {
            holder.status.setText("Asked");
            holder.new_status.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }
}
