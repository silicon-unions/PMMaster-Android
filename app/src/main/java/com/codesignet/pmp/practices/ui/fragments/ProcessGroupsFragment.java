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
import com.codesignet.pmp.practices.adapters.ProcessGroupsAdapter;
import com.codesignet.pmp.practices.data_access_layer.KnowledgeAreasObject;
import com.codesignet.pmp.practices.data_access_layer.ProcessGroupsObject;
import com.codesignet.pmp.practices.view.OnPracticesTypeClicked;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class ProcessGroupsFragment extends Fragment {

    @BindView(R.id.rc_pg)
    RecyclerView rv_process_groups;

    private ArrayList<ProcessGroupsObject> processGroupsList;
    private ProcessGroupsAdapter processGroupsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnPracticesTypeClicked listeners;

    public static Fragment newInstance(OnPracticesTypeClicked listeners) {
        ProcessGroupsFragment ProcessGroupsFragment = new ProcessGroupsFragment();
        ProcessGroupsFragment.listeners = listeners;
        return ProcessGroupsFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_process_groups, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        processGroupsList = new ArrayList();
        fillProcessGroupsList();
        processGroupsAdapter = new ProcessGroupsAdapter(processGroupsList, listeners);
        InitializeRecyclerView();
    }

    private void InitializeRecyclerView() {
        rv_process_groups.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rv_process_groups.setLayoutManager(mLayoutManager);
        rv_process_groups.setAdapter(processGroupsAdapter);
    }
    private void fillProcessGroupsList() {
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        processGroupsList.add(new ProcessGroupsObject(Constants.Initiating_Process_Group,Integer.valueOf(prefs.getString(Constants.Initiating_Process_Group, "0"))));
        processGroupsList.add(new ProcessGroupsObject(Constants.Planning_Process_Group,Integer.valueOf(prefs.getString(Constants.Planning_Process_Group, "0"))));
        processGroupsList.add(new ProcessGroupsObject(Constants.Executing_Process_Group,Integer.valueOf(prefs.getString(Constants.Executing_Process_Group, "0"))));
        processGroupsList.add(new ProcessGroupsObject(Constants.Monitoring_and_Controlling_Process_Group,Integer.valueOf(prefs.getString(Constants.Monitoring_and_Controlling_Process_Group, "0"))));
        processGroupsList.add(new ProcessGroupsObject(Constants.Closing_Process_Group,Integer.valueOf(prefs.getString(Constants.Closing_Process_Group, "0"))));
    }
}