package com.codesignet.pmp.home.ui.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.progresviews.ProgressWheel;
import com.codesignet.pmp.BuildConfig;
import com.codesignet.pmp.R;
import com.codesignet.pmp.app.Constants;
import com.codesignet.pmp.exam.data_access_layer.database_manager.ExamDatabaseHelper;
import com.codesignet.pmp.home.data_access_layer.AllStatisticsObject;
import com.codesignet.pmp.home.data_access_layer.ToadyExamDatabaseHelper;
import com.codesignet.pmp.home.data_access_layer.TodayExamObject;
import com.codesignet.pmp.home.ui.AllRecordValues;
import com.codesignet.pmp.home.ui.adapter.AllDataAdapter;
import com.codesignet.pmp.home.view.HomeView;
import com.codesignet.pmp.practices.data_access_layer.database_manager.PracticesDatabaseHelper;
import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {


    @BindView(R.id.tv_exam_counter)
    TextView tv_exam_qounter;
    @BindView(R.id.tv_exam_preparation_message)
    TextView tv_exam_preparation_message;
    @BindView(R.id.exam_time)
    LinearLayout exam_time;
    @BindView(R.id.rv_all_data)
    RecyclerView rvAllData;
    @BindView(R.id.practices_progress)
    ProgressWheel practicesProgressWheel;
    @BindView(R.id.exam_progress)
    ProgressWheel examProgressWheel;

    @BindView(R.id.Time_progress)
    ProgressWheel timeProgress;

    @BindView(R.id.app_time_card)
    CardView app_time_card;

    @BindView(R.id.exam_card)
    CardView exam_card;

    @BindView(R.id.exam_per_day_card)
    CardView exam_per_day_card;
    @BindView(R.id.practices_card)
    CardView practices_card;

    @BindView(R.id.exam_per_day_progress)
    FitChart chart;

    @BindView(R.id.practices_count)
    TextView practices_count;
    @BindView(R.id.premium_layout)
    LinearLayout premium_layout;
    Collection<FitChartValue> values = new ArrayList<>();

    private AllStatisticsObject examsData;
    private AllStatisticsObject practicesData;
    private RecyclerView.LayoutManager mLayoutManager;
    private HomeView callback;
    private SharedPreferences.Editor editor;
    private AllDataAdapter processGroupsAdapter;
    private ArrayList<AllRecordValues> processGroupsList;
    private TodayExamObject todayExamObject;

    public static HomeFragment newInstance(HomeView callback) {
        HomeFragment fragment = new HomeFragment();
        fragment.callback = callback;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editor = getActivity().getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).edit();
        ToadyExamDatabaseHelper databaseHelper = new ToadyExamDatabaseHelper(getActivity());
        todayExamObject = databaseHelper.getTodayExamData(convertLongDataToString(System.currentTimeMillis()));
        setTodayExamData(todayExamObject);
        setExamData();
        setPracticesData();
        showExamCountDown();
        showAppTime();
        processGroupsList = new ArrayList();
        fillProcessGroupsList();
        processGroupsAdapter = new AllDataAdapter(processGroupsList);
        InitializeRecyclerView();

        if (BuildConfig.FLAVOR.equals("Free")) {
            premium_layout.setVisibility(View.VISIBLE);
        } else if (BuildConfig.FLAVOR.equals("Paid")) {
            premium_layout.setVisibility(View.GONE);
        } else {
            premium_layout.setVisibility(View.GONE);
        }
    }

    private void setTodayExamData(TodayExamObject todayExamObject) {
        if (todayExamObject != null) {
            exam_per_day_card.setVisibility(View.VISIBLE);
            values.add(new FitChartValue(todayExamObject.getCorrectAnswer(), getResources().getColor(R.color.green)));
            values.add(new FitChartValue(todayExamObject.getWrongAnswer(), getResources().getColor(R.color.orange_pink)));
            chart.setMinValue(0f);
            chart.setMaxValue(todayExamObject.getWrongAnswer() + todayExamObject.getCorrectAnswer());
            chart.setValues(values);
        }
    }

    private String convertLongDataToString(long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    private void showAppTime() {
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        if (prefs.getInt(Constants.APP_TIME, 0) != 0) {
            app_time_card.setVisibility(View.VISIBLE);
            timeProgress.setPercentage(360);
            timeProgress.setPercentage((int) (prefs.getInt(Constants.APP_TIME, 0) * 3.6));
            timeProgress.setStepCountText(String.valueOf(prefs.getInt(Constants.APP_TIME, 0)));
        }
    }

    private void setPracticesData() {
        PracticesDatabaseHelper practicesDatabaseHelper = new PracticesDatabaseHelper(getActivity());
        practicesData = practicesDatabaseHelper.getAllAllStatistics();
        int count = practicesDatabaseHelper.getPracticesCount();
        practices_count.setText("Goal : " + practicesDatabaseHelper.getPracticesCount() + " Question");
        if (practicesData.getCorrectAnswers() != 0) {
            practices_card.setVisibility(View.VISIBLE);
            practicesProgressWheel.setPercentage(360);
            practicesProgressWheel.setPercentage((int) ((practicesData.getCorrectAnswers() / count) * 360 * 3.6));
            practicesProgressWheel.setStepCountText(String.valueOf(practicesData.getCorrectAnswers()));
        }
    }

    @OnClick(R.id.premium_layout)
    public void onClickpremium_layout(){
        showExamDialog("Upgrade", "Upgrade to have access to full version", false);
    }
    public void showExamDialog(String title, String message, boolean hideNoButtom) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("Upgrade", (dialog, id) -> {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.codesignet.pmp.paid")));
                    dialog.cancel();
                });
        if (!hideNoButtom) {
            builder.setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
        }
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void setExamData() {
        ExamDatabaseHelper examsDatabaseHelper = new ExamDatabaseHelper(getActivity());
        examsData = examsDatabaseHelper.getAllAllStatistics();
        if (examsData.getAllQuestions() != 0) {
            exam_card.setVisibility(View.VISIBLE);
            examProgressWheel.setPercentage(360);
            examProgressWheel.setPercentage((int) ((examsData.getCorrectAnswers() / examsData.getAllQuestions()) * 3.6));
            examProgressWheel.setStepCountText(String.valueOf(examsData.getAllQuestions()));
        }
    }

    private void showExamCountDown() {
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        final long startTime = System.currentTimeMillis();
        if (prefs.getLong(Constants.Exam_TIME, 0) == startTime) {
            exam_time.setVisibility(View.VISIBLE);
            tv_exam_qounter.setText("Exam Today");
        }
        if (prefs.getLong(Constants.Exam_TIME, 0) == 0) {
            exam_time.setVisibility(View.GONE);
        } else {
            exam_time.setVisibility(View.VISIBLE);
            daysCountDown(prefs.getLong(Constants.Exam_TIME, 0));
        }
    }

    private void InitializeRecyclerView() {
        rvAllData.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvAllData.setLayoutManager(mLayoutManager);
        rvAllData.setAdapter(processGroupsAdapter);
    }

    private void fillProcessGroupsList() {
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        processGroupsList.add(new AllRecordValues(Constants.Initiating_Process_Group, Integer.valueOf(prefs.getString(Constants.Initiating_Process_Group, "0"))));
        processGroupsList.add(new AllRecordValues(Constants.Planning_Process_Group, Integer.valueOf(prefs.getString(Constants.Planning_Process_Group, "0"))));
        processGroupsList.add(new AllRecordValues(Constants.Executing_Process_Group, Integer.valueOf(prefs.getString(Constants.Executing_Process_Group, "0"))));
        processGroupsList.add(new AllRecordValues(Constants.Monitoring_and_Controlling_Process_Group, Integer.valueOf(prefs.getString(Constants.Monitoring_and_Controlling_Process_Group, "0"))));
        processGroupsList.add(new AllRecordValues(Constants.Closing_Process_Group, Integer.valueOf(prefs.getString(Constants.Closing_Process_Group, "0"))));
        processGroupsList.add(new AllRecordValues(Constants.Project_integration_management, Integer.valueOf(prefs.getString(Constants.Project_integration_management, "0"))));
        processGroupsList.add(new AllRecordValues(Constants.Project_scope_management, Integer.valueOf(prefs.getString(Constants.Project_scope_management, "0"))));
        processGroupsList.add(new AllRecordValues(Constants.Project_schedule_management, Integer.valueOf(prefs.getString(Constants.Project_schedule_management, "0"))));
        processGroupsList.add(new AllRecordValues(Constants.Project_cost_management, Integer.valueOf(prefs.getString(Constants.Project_cost_management, "0"))));
        processGroupsList.add(new AllRecordValues(Constants.Project_quality_management, Integer.valueOf(prefs.getString(Constants.Project_quality_management, "0"))));
        processGroupsList.add(new AllRecordValues(Constants.Project_resource_management, Integer.valueOf(prefs.getString(Constants.Project_resource_management, "0"))));
        processGroupsList.add(new AllRecordValues(Constants.Project_communications_management, Integer.valueOf(prefs.getString(Constants.Project_communications_management, "0"))));
        processGroupsList.add(new AllRecordValues(Constants.Project_risk_management, Integer.valueOf(prefs.getString(Constants.Project_risk_management, "0"))));
        processGroupsList.add(new AllRecordValues(Constants.Project_procurement_management, Integer.valueOf(prefs.getString(Constants.Project_procurement_management, "0"))));
        processGroupsList.add(new AllRecordValues(Constants.Project_stakeholder_management, Integer.valueOf(prefs.getString(Constants.Project_stakeholder_management, "0"))));
    }

    public void daysCountDown(long counterTime) {
        final long startTime = System.currentTimeMillis();
        new CountDownTimer(counterTime, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Long serverUptimeSeconds = (millisUntilFinished - startTime) / 1000;
                editor.putLong(Constants.Exam_TIME, serverUptimeSeconds);
                String daysLeft = String.format("%d", serverUptimeSeconds / 86400);
                if (daysLeft.compareTo("0") == 0) {
                    tv_exam_qounter.setText("Exam is today!");
                } else if (daysLeft.compareTo("1") == 0) {
                    tv_exam_qounter.setText("Exam is tomorrow!");
                } else {
                    tv_exam_qounter.setText(daysLeft + " days left");
                }
            }

            @Override
            public void onFinish() {
                clearSharedPreferences();
            }
        }.start();

    }

    @OnClick(R.id.btn_10_question)
    public void goTOQuickQuestion() {
        callback.navigateTenQuestionScreen();
    }

    @OnClick(R.id.textView67)
    public void goToTodayExamData() {
        callback.navigateToTodayExamDataScreen();
    }

    @OnClick(R.id.textView6)
    public void goToLastExam() {
        callback.navigateToLastExamScreen();
    }

    public void clearSharedPreferences() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        preferences.edit().remove("exam_date").apply();
    }
}