package com.codesignet.pmp.exam.view;

import com.codesignet.pmp.exam.data_access_layer.pojos.ExamData;
import com.codesignet.pmp.exam.data_access_layer.pojos.ExamOption;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionsItem;

import java.util.ArrayList;

public interface OnNavigateListener {
    void navigateExamScreen(ExamOption option);
    void navigateTenStatisticsScreen(ExamData examData);
}
