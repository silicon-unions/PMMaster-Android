package com.codesignet.pmp.exam.ui.fragments;

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
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionsItem;
import com.codesignet.pmp.exam.ui.adapter.QuestionAdapter;
import com.codesignet.pmp.exam.view.onSelectQuestion;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamQuestionListFragment extends Fragment {

    @BindView(R.id.rv_questions_list)
    RecyclerView questionList;

    private ArrayList<QuestionsItem> questions;
    private QuestionAdapter questionAdapter;
    private onSelectQuestion callback;


    public static ExamQuestionListFragment newInstance(ArrayList<QuestionsItem> questionsList, onSelectQuestion callback) {
        ExamQuestionListFragment fragment = new ExamQuestionListFragment();
        fragment.callback = callback;
        fragment.questions = questionsList;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_list, container, false);
        ButterKnife.bind(this, view);
        InitializeRecyclerView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void InitializeRecyclerView() {
        questionAdapter = new QuestionAdapter(questions, getActivity(), callback, "exam");
        questionList.setLayoutManager(new LinearLayoutManager(getActivity()));
        questionList.setAdapter(questionAdapter);
        questionList.setHasFixedSize(true);
    }
}