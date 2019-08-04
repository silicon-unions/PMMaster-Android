package com.codesignet.pmp.home.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.exam.data_access_layer.database_manager.ExamDatabaseHelper;
import com.codesignet.pmp.home.data_access_layer.AllStatisticsObject;
import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllStatisticsFragment extends Fragment {

    @BindView(R.id.all_statistics_chart)
    FitChart chart;
    @BindView(R.id.textView)
    TextView percentatge;

    Collection<FitChartValue> values = new ArrayList<>();
    AllStatisticsObject object;

    public static AllStatisticsFragment newInstance() {
        AllStatisticsFragment fragmentFirst = new AllStatisticsFragment();
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExamDatabaseHelper databaseHelper = new ExamDatabaseHelper(getActivity());
        object = databaseHelper.getAllAllStatistics();
        values.add(new FitChartValue(Float.valueOf(String.valueOf(object.getCorrectAnswers())), getResources().getColor(R.color.green)));
        values.add(new FitChartValue(Float.valueOf(String.valueOf(object.getWrongAnswers())), getResources().getColor(R.color.orange_pink)));
        values.add(new FitChartValue(Float.valueOf(String.valueOf(object.getUnresolvedAnswers())), getResources().getColor(R.color.paleOrange)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_statistics, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chart.setMinValue(0f);
        if(object.getAllQuestions() >0) {
            percentatge.setText(String.valueOf((object.getCorrectAnswers() * 100) / object.getAllQuestions()) + " %");
        }else{
            percentatge.setText("0 %");
        }
        chart.setMaxValue(object.getAllQuestions());
        chart.setValues(values);
    }

    @OnClick(R.id.btn_lastExam)
    public void goToLastExam() {
        AppRouter.navigateToLastExam(getActivity());
    }
}