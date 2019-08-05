package com.codesignet.pmp.home.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.progresviews.ProgressLine;
import com.codesignet.pmp.R;
import com.codesignet.pmp.practices.data_access_layer.ProcessGroupsObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllDataViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.progress_line)
    ProgressLine progress_line;
    @BindView(R.id.pg_title)
    TextView textView;
    @BindView(R.id.percentage)
    TextView percentageTextView;
    private View card;
    public AllDataViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        card = itemView;
    }

    public void bindView(AllRecordValues object) {
        int value  = (object.getRecordLevel()-1);
        percentageTextView.setText(value*10+"%");
        textView.setText(object.getRecordName());
        progress_line.setmPercentage((object.getRecordLevel()-1)*10);
    }
}
