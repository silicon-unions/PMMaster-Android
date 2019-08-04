package com.codesignet.pmp.home.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.codesignet.pmp.R;
import com.codesignet.pmp.home.data_access_layer.ToadyExamDatabaseHelper;
import com.codesignet.pmp.home.data_access_layer.TodayExamObject;
import com.codesignet.pmp.home.ui.adapter.TodayExamDataAdapter;
import com.codesignet.pmp.practices.adapters.KnowledgeAreasAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodayExamDataActivity extends AppCompatActivity {

    @BindView(R.id.rc_today_list)
    RecyclerView rc_today_list;
    @BindView(R.id.warrnign_message)
    TextView warrnign_message;

    private ArrayList<TodayExamObject> KnowledgeAreasList;
    private TodayExamDataAdapter todayExamDataAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_exam_data);
        ButterKnife.bind(this);

        ToadyExamDatabaseHelper databaseHelper = new ToadyExamDatabaseHelper(getApplicationContext());

        KnowledgeAreasList = databaseHelper.getTodayExamHistory();
        if (KnowledgeAreasList.size() != 0) {
            rc_today_list.setVisibility(View.VISIBLE);
            todayExamDataAdapter = new TodayExamDataAdapter(KnowledgeAreasList);
            InitializeRecyclerView();
        } else {
            warrnign_message.setVisibility(View.VISIBLE);
        }
    }

    private void InitializeRecyclerView() {
        rc_today_list.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_today_list.setLayoutManager(mLayoutManager);
        rc_today_list.setAdapter(todayExamDataAdapter);
    }
}
