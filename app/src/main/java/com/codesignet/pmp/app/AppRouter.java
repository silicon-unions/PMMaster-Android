package com.codesignet.pmp.app;

import android.content.Context;
import android.content.Intent;

import com.codesignet.pmp.Authentication.ui.activity.ForgerPasswordActivity;
import com.codesignet.pmp.Authentication.ui.activity.LoginActivity;
import com.codesignet.pmp.Authentication.ui.activity.SignUpActivity;
import com.codesignet.pmp.ChooseActivity;
import com.codesignet.pmp.ask_instructor.ui.activity.AskScreenActivity;
import com.codesignet.pmp.exam.data_access_layer.pojos.ExamData;
import com.codesignet.pmp.exam.data_access_layer.pojos.ExamOption;
import com.codesignet.pmp.exam.ui.activity.ExamHistoryActivity;
import com.codesignet.pmp.exam.ui.activity.ExamOptionActivity;
import com.codesignet.pmp.exam.ui.fragments.ExamFragment;
import com.codesignet.pmp.home.ui.activity.HomeActivity;
import com.codesignet.pmp.home.ui.activity.TodayExamDataActivity;
import com.codesignet.pmp.notes.ui.activity.NotesScreenActivity;
import com.codesignet.pmp.practices.ui.activities.PracticeOptionActivity;
import com.codesignet.pmp.practices.ui.activities.PracticesActivity;
import com.codesignet.pmp.practices.ui.activities.PracticesDownloadActivity;
import com.codesignet.pmp.practices.ui.activities.PracticesLevelsActivity;
import com.codesignet.pmp.profile.ui.activity.ProfileActivityScreen;
import com.codesignet.pmp.statistics.StatisticsActivity;

public class AppRouter {
    public static void navigateToHomeScreen(Context context) {
        Intent gotoHomeScreen = new Intent(context, HomeActivity.class);
        gotoHomeScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(gotoHomeScreen);
    }

    public static void navigateToNoteScreen(Context context) {
        Intent gotoNoteScreen = new Intent(context, NotesScreenActivity.class);
        gotoNoteScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(gotoNoteScreen);
    }

    public static void navigateToExamScreen(Context context, ExamOption option) {
        Intent gotoExam = new Intent(context, ExamFragment.class);
        gotoExam.putExtra(Constants.EXAM_OPTIONS, option);
        gotoExam.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(gotoExam);
    }

    public static void navigateToExamOptionScreen(Context context) {
        Intent gotoExam = new Intent(context, ExamOptionActivity.class);
        gotoExam.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(gotoExam);
    }

    public static void navigateToLoginScreen(Context context) {
        Intent gotoLogin = new Intent(context, LoginActivity.class);
        gotoLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(gotoLogin);
    }

    public static void navigateToForgetPasswordScreen(Context context) {
        Intent forgetPassword = new Intent(context, ForgerPasswordActivity.class);
        forgetPassword.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(forgetPassword);
    }

    public static void navigateToSignUpScreen(Context context) {
        Intent forgetPassword = new Intent(context, SignUpActivity.class);
        forgetPassword.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(forgetPassword);
    }

    public static void navigateToAskInstructorScreen(Context context) {
        Intent askInstructor = new Intent(context, AskScreenActivity.class);
        askInstructor.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(askInstructor);
    }

    public static void navigateToProfileScreen(Context context) {
        Intent askInstructor = new Intent(context, ProfileActivityScreen.class);
        askInstructor.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(askInstructor);
    }

    public static void navigateTenStatisticsScreen(Context context, ExamData data) {
        Intent gotoStatistics = new Intent(context, StatisticsActivity.class);
        gotoStatistics.putExtra(Constants.EXAM_DATA, data);
        gotoStatistics.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(gotoStatistics);
    }

    public static void navigateToChoose(Context context) {
        Intent gotoLogin = new Intent(context, ChooseActivity.class);
        gotoLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(gotoLogin);
    }


    public static void navigateToLastExam(Context context) {
        Intent goTo = new Intent(context, ExamHistoryActivity.class);
        goTo.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(goTo);
    }

    public static void navigateToPracticesLevel(Context context, String selectedItemPosition, boolean isProcessGroup) {
        Intent goTo = new Intent(context, PracticesLevelsActivity.class);
        goTo.putExtra("user_selection", selectedItemPosition);
        goTo.putExtra("is_process_group", isProcessGroup);
        goTo.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(goTo);
    }

    public static void navigateToPractices(Context context) {
        Intent goTo = new Intent(context, PracticeOptionActivity.class);
        goTo.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(goTo);
    }

    public static void navigateToPracticesActivity(Context context, int currentLevel, String selectedItemPosition, boolean isProcessGroup) {
        Intent goTo = new Intent(context, PracticesActivity.class);
        goTo.putExtra("user_selection", selectedItemPosition);
        goTo.putExtra("currentLevel", currentLevel);
        goTo.putExtra("is_process_group", isProcessGroup);
        goTo.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(goTo);
    }

    public static void navigateToPracticesDownlaod(Context context) {
        Intent goTo = new Intent(context, PracticesDownloadActivity.class);
        goTo.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(goTo);
    }

    public static void navigateToPracticesActivity(Context context) {
        Intent goTo = new Intent(context, PracticesActivity.class);
        goTo.putExtra("ten_question", true);
        goTo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(goTo);
    }

    public static void navigateTodayExamDataActivity(Context context) {
        Intent goTo = new Intent(context, TodayExamDataActivity.class);
        goTo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(goTo);
    }
}
