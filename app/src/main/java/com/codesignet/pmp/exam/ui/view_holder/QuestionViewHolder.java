package com.codesignet.pmp.exam.ui.view_holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codesignet.pmp.R;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionsItem;
import com.codesignet.pmp.exam.view.onSelectQuestion;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_question_number)
    TextView question_number;
    @BindView(R.id.question_title)
    TextView title;
    @BindView(R.id.iv_mark_question)
    ImageView flag;
    @BindView(R.id.iv_answer)
    ImageView iv_answer;

    private View itemView;

    public QuestionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    public void bindView(QuestionsItem questionsItem, onSelectQuestion callback, int position, Context context, String type) {
        question_number.setText(String.valueOf(position + 1));
        title.setText(questionsItem.getQuestionE());
        if (type.equals("histroy")) {
            if (questionsItem.getUserAnswer() != null) {
                if (questionsItem.getUserAnswer().equals(questionsItem.getAnswer())) {
                    iv_answer.setVisibility(View.VISIBLE);
                    iv_answer.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon));
                } else {
                    iv_answer.setVisibility(View.VISIBLE);
                    iv_answer.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_cancel));
                }
            } else {
                iv_answer.setVisibility(View.VISIBLE);
                iv_answer.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_cancel));
            }
        } else {
            iv_answer.setVisibility(View.GONE);
            if (questionsItem.isFlag())
                flag.setVisibility(View.VISIBLE);
            else
                flag.setVisibility(View.GONE);
        }
        itemView.setOnClickListener((v) -> callback.showQuestionByNumber(position));
    }
}
