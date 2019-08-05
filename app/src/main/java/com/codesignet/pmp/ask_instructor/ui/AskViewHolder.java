package com.codesignet.pmp.ask_instructor.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codesignet.pmp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AskViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.question_title)
    public TextView question;

    @BindView(R.id.time)
    public TextView time;
    @BindView(R.id.question_status)
    public TextView status;

    @BindView(R.id.iv_delete_question)
    public ImageView delete_question;

    @BindView(R.id.iv_new_status)
    public ImageView new_status;

    public AskViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
