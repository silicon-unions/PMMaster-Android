package com.codesignet.pmp.practices.ui.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.progresviews.ProgressWheel;
import com.codesignet.pmp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LevelViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.level_number)
    public TextView level_number;

    @BindView(R.id.level_question)
    public TextView level_question;

    @BindView(R.id.level_progress)
    public ProgressWheel level_progress;

    @BindView(R.id.lock)
    public ImageView lock;

    public LevelViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
