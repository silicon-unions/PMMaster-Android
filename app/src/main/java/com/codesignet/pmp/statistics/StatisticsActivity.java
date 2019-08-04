package com.codesignet.pmp.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.app.Constants;
import com.codesignet.pmp.exam.data_access_layer.pojos.ExamData;
import com.codesignet.pmp.home.data_access_layer.ToadyExamDatabaseHelper;
import com.codesignet.pmp.home.data_access_layer.TodayExamObject;
import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatisticsActivity extends AppCompatActivity {

    @BindView(R.id.all_statistics_chart)
    FitChart chart;

    @BindView(R.id.tv_correct)
    TextView tv_correct;

    @BindView(R.id.tv_unresolved)
    TextView tv_unresolved;

    @BindView(R.id.tv_wrongs)
    TextView tv_wrongs;

    @BindView(R.id.btn_review_questions)
    Button review;

    Collection<FitChartValue> values = new ArrayList<>();
    private ExamData data;
    private TodayExamObject todayExamObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);
        Intent i = getIntent();
        data = (ExamData) i.getSerializableExtra(Constants.EXAM_DATA);
        if (data.isTenQuestionType()) {
            review.setVisibility(View.GONE);
        }
        tv_correct.setText(String.valueOf(data.getCorrectAnswers()));
        tv_wrongs.setText(String.valueOf(data.getWrongAnswers()));
        tv_unresolved.setText(String.valueOf(data.getUnSolvedQuestions()));
        values.add(new FitChartValue(data.getCorrectAnswers(), getResources().getColor(R.color.green)));
        values.add(new FitChartValue(data.getWrongAnswers(), getResources().getColor(R.color.orange_pink)));
        values.add(new FitChartValue(data.getUnSolvedQuestions(), getResources().getColor(R.color.paleOrange)));
        chart.setMinValue(0f);
        chart.setMaxValue(data.getCorrectAnswers() + data.getWrongAnswers() + data.getUnSolvedQuestions());
        chart.setValues(values);
    }

    @OnClick(R.id.btn_done)
    public void onDone() {
        ToadyExamDatabaseHelper databaseHelper = new ToadyExamDatabaseHelper(getApplicationContext());
        databaseHelper.updateTodayExamData(convertLongDataToString(System.currentTimeMillis()), data.getCorrectAnswers(), data.getWrongAnswers());
        AppRouter.navigateToHomeScreen(getApplicationContext());
        finish();

    }

    @OnClick(R.id.btn_review_questions)
    public void btn_review_questions() {
        AppRouter.navigateToLastExam(getApplicationContext());
        finish();
    }


    private String convertLongDataToString(long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
}