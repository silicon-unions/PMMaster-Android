package com.codesignet.pmp.home.ui.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.progresviews.ProgressLine;
import com.codesignet.pmp.R;
import com.codesignet.pmp.home.data_access_layer.TodayExamObject;
import com.codesignet.pmp.practices.data_access_layer.KnowledgeAreasObject;
import com.codesignet.pmp.practices.view.OnPracticesTypeClicked;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodayExamDataViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.tv_date_value)
    TextView tv_date_value;

    @BindView(R.id.tv_correct_value)
    TextView tv_correct_value;

    @BindView(R.id.tv_Wrong_value)
    TextView tv_Wrong_value;


    public TodayExamDataViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(TodayExamObject object) {
        tv_Wrong_value.setText(String.valueOf(object.getWrongAnswer()));
        tv_correct_value.setText(String.valueOf(object.getCorrectAnswer()));
        tv_date_value.setText(object.getExamDate());
    }
}
