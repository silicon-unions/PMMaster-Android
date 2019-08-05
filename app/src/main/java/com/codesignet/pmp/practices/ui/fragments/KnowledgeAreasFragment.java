package com.codesignet.pmp.practices.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codesignet.pmp.R;
import com.codesignet.pmp.app.Constants;
import com.codesignet.pmp.practices.adapters.KnowledgeAreasAdapter;
import com.codesignet.pmp.practices.data_access_layer.KnowledgeAreasObject;
import com.codesignet.pmp.practices.data_access_layer.pojos.Levels;
import com.codesignet.pmp.practices.view.OnPracticesTypeClicked;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class KnowledgeAreasFragment extends Fragment {

    @BindView(R.id.rc_ka)
    RecyclerView rv_process_groups;

    private ArrayList<KnowledgeAreasObject> KnowledgeAreasList;
    private KnowledgeAreasAdapter KnowledgeAreasAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnPracticesTypeClicked listeners;

    public static Fragment newInstance(OnPracticesTypeClicked listeners) {
        KnowledgeAreasFragment KnowledgeAreasFragment = new KnowledgeAreasFragment();
        KnowledgeAreasFragment.listeners = listeners;
        return KnowledgeAreasFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_knowledge_areas, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        KnowledgeAreasList  = new ArrayList();
        fillKnowledgeAreasList();
        KnowledgeAreasAdapter = new KnowledgeAreasAdapter(KnowledgeAreasList, listeners);
        InitializeRecyclerView();
    }

    private void InitializeRecyclerView() {
        rv_process_groups.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rv_process_groups.setLayoutManager(mLayoutManager);
        rv_process_groups.setAdapter(KnowledgeAreasAdapter);
    }
    private void fillKnowledgeAreasList(){
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        KnowledgeAreasList.add(new KnowledgeAreasObject(Constants.Project_integration_management,Integer.valueOf(prefs.getString(Constants.Project_integration_management, "0"))));
        KnowledgeAreasList.add(new KnowledgeAreasObject(Constants.Project_scope_management,Integer.valueOf(prefs.getString(Constants.Project_scope_management, "0"))));
        KnowledgeAreasList.add(new KnowledgeAreasObject(Constants.Project_schedule_management,Integer.valueOf(prefs.getString(Constants.Project_schedule_management, "0"))));
        KnowledgeAreasList.add(new KnowledgeAreasObject(Constants.Project_cost_management,Integer.valueOf(prefs.getString(Constants.Project_cost_management, "0"))));
        KnowledgeAreasList.add(new KnowledgeAreasObject(Constants.Project_quality_management,Integer.valueOf(prefs.getString(Constants.Project_quality_management, "0"))));
        KnowledgeAreasList.add(new KnowledgeAreasObject(Constants.Project_resource_management,Integer.valueOf(prefs.getString(Constants.Project_resource_management, "0"))));
        KnowledgeAreasList.add(new KnowledgeAreasObject(Constants.Project_communications_management,Integer.valueOf(prefs.getString(Constants.Project_communications_management, "0"))));
        KnowledgeAreasList.add(new KnowledgeAreasObject(Constants.Project_risk_management,Integer.valueOf(prefs.getString(Constants.Project_risk_management, "0"))));
        KnowledgeAreasList.add(new KnowledgeAreasObject(Constants.Project_procurement_management,Integer.valueOf(prefs.getString(Constants.Project_procurement_management, "0"))));
        KnowledgeAreasList.add(new KnowledgeAreasObject(Constants.Project_stakeholder_management,Integer.valueOf(prefs.getString(Constants.Project_stakeholder_management, "0"))));
    }
}