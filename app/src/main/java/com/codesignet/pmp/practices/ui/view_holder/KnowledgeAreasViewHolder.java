package com.codesignet.pmp.practices.ui.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.progresviews.ProgressLine;
import com.codesignet.pmp.R;
import com.codesignet.pmp.practices.data_access_layer.KnowledgeAreasObject;
import com.codesignet.pmp.practices.view.OnPracticesTypeClicked;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KnowledgeAreasViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.progress_line)
    ProgressLine progress_line;

    @BindView(R.id.pg_title)
    TextView textView;

    @BindView(R.id.percentage)
    TextView percentageTextView;

    private View card;

    public KnowledgeAreasViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        card = itemView;
    }

    public void bindView(OnPracticesTypeClicked callBack, KnowledgeAreasObject object) {
        int value  = (object.getKnowledgeAreasLevel()-1);
        percentageTextView.setText(value*10+"%");
        textView.setText(object.getKnowledgeAreasName());
        progress_line.setmPercentage((object.getKnowledgeAreasLevel()-1)*10);
        card.setOnClickListener((View v) -> callBack.onKnowledgeAreasClicked(object));
    }
}
