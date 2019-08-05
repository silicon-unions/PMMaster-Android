package com.codesignet.pmp.practices.ui.activities;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.app.progresviews.ProgressLine;
import com.codesignet.pmp.BuildConfig;
import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.app.Constants;
import com.codesignet.pmp.basics.BaseActivity;
import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.practices.data_access_layer.PracticesInteractor;
import com.codesignet.pmp.practices.data_access_layer.database_manager.PracticesDatabaseHelper;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesListResponse;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesObject;
import com.codesignet.pmp.practices.data_access_layer.pojos.UserLevels;
import com.codesignet.pmp.practices.dependencies.DaggerPracticesComponent;
import com.codesignet.pmp.practices.dependencies.PracticesModule;
import com.codesignet.pmp.practices.presenter.PracticesPresenter;
import com.codesignet.pmp.practices.view.PracticesView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PracticesDownloadActivity extends
        BaseActivity<PracticesInteractor, PracticesView, PracticesPresenter>
        implements PracticesView {

    @BindView(R.id.progress_line)
    ProgressLine progressLine;
    int x = 0;
    private int levelNumber = 1;
    private int triesnumber = 0;
    private PracticesObject practicesObject;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitializeDagger();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practices_download);
        ButterKnife.bind(this);
        PracticesDatabaseHelper databaseHelper = new PracticesDatabaseHelper(getApplicationContext());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        databaseHelper.onUpgrade(db, 0, 0);
        db.close();
        practicesObject = new PracticesObject();
        editor = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).edit();
        getQuestionsByLevel(getAccessToken());
    }

    private void InitializeDagger() {
        DaggerPracticesComponent
                .builder()
                .practicesModule(new PracticesModule(this))
                .build()
                .inject(this);
    }

    private void getQuestionsByLevel(String apiToken) {
        practicesObject.setLevel(levelNumber);
        mPresenter.getLevelQuestions(practicesObject, apiToken);
    }

    @Override
    public void onSuccess(PracticesListResponse response) {
        if(BuildConfig.FLAVOR.equals("Free")){
            editor.putBoolean(Constants.ALL_QUESTIONS_DOWNLOADED, true);
            editor.putString(Constants.Initiating_Process_Group, "1");
            editor.putString(Constants.Planning_Process_Group, "1");
            editor.putString(Constants.Executing_Process_Group, "1");
            editor.putString(Constants.Monitoring_and_Controlling_Process_Group, "1");
            editor.putString(Constants.Closing_Process_Group, "1");
            editor.putString(Constants.Project_integration_management, "1");
            editor.putString(Constants.Project_scope_management,"1");
            editor.putString(Constants.Project_schedule_management, "1");
            editor.putString(Constants.Project_cost_management, "1");
            editor.putString(Constants.Project_quality_management, "1");
            editor.putString(Constants.Project_resource_management, "1");
            editor.putString(Constants.Project_communications_management, "1");
            editor.putString(Constants.Project_risk_management, "1");
            editor.putString(Constants.Project_procurement_management,"1");
            editor.putString(Constants.Project_stakeholder_management, "1");
            editor.apply();
            progressLine.setmValueText("100%");
            progressLine.setmPercentage(100);
            mPresenter.addQuestions(response.getQuestions(), getApplicationContext());
            AppRouter.navigateToHomeScreen(getApplicationContext());
            finish();
        }
        else if(BuildConfig.FLAVOR.equals("Paid")){
            progressLine.setmValueText(x+"%");
            progressLine.setmPercentage(x);

            if (levelNumber <= 10) {
                triesnumber = 0;
                levelNumber++;
                x += 10;
                if (mPresenter.addQuestions(response.getQuestions(), getApplicationContext())) {
                    getQuestionsByLevel(getAccessToken());
                }
            } else {
                mPresenter.getUserLevels(getAccessToken());
                triesnumber = 3;
            }
        }else{
            Toast.makeText(this, "HELLO UNKNOWN", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLevelsReceived(UserLevels userLevels) {
        editor.putBoolean(Constants.ALL_QUESTIONS_DOWNLOADED, true);
        editor.putString(Constants.Initiating_Process_Group, userLevels.getLevels().getInitiatingProcessGroup());
        editor.putString(Constants.Planning_Process_Group, userLevels.getLevels().getPlanningProcessGroup());
        editor.putString(Constants.Executing_Process_Group, userLevels.getLevels().getExecutingProcessGroup());
        editor.putString(Constants.Monitoring_and_Controlling_Process_Group, userLevels.getLevels().getMonitoringAndControllingProcessGroup());
        editor.putString(Constants.Closing_Process_Group, userLevels.getLevels().getClosingProcessGroup());
        editor.putString(Constants.Project_integration_management, userLevels.getLevels().getProjectIntegrationManagement());
        editor.putString(Constants.Project_scope_management, userLevels.getLevels().getProjectScopeManagement());
        editor.putString(Constants.Project_schedule_management, userLevels.getLevels().getProjectScheduleManagement());
        editor.putString(Constants.Project_cost_management, userLevels.getLevels().getProjectCostManagement());
        editor.putString(Constants.Project_quality_management, userLevels.getLevels().getProjectQualityManagement());
        editor.putString(Constants.Project_resource_management, userLevels.getLevels().getProjectResourceManagement());
        editor.putString(Constants.Project_communications_management, userLevels.getLevels().getProjectCommunicationsManagement());
        editor.putString(Constants.Project_risk_management, userLevels.getLevels().getProjectRiskManagement());
        editor.putString(Constants.Project_procurement_management, userLevels.getLevels().getProjectProcurementManagement());
        editor.putString(Constants.Project_stakeholder_management, userLevels.getLevels().getProjectStakeholderManagement());
        editor.apply();
        AppRouter.navigateToHomeScreen(getApplicationContext());
        finish();
    }

    @Override
    public void onSuccess(BaseResponse response) {
    }

    @Override
    public void onFailure(Throwable e) {
        triesnumber++;
        Log.i("Doby ", e.getMessage());
        if (triesnumber < 3)
            getQuestionsByLevel(getAccessToken());
        else
            showExamDialog("Connection error", "Unable to connect to the server." +
                    " \n Check your internet connection and try the again.");
    }

    @Override
    public void onLevelClicked(int level) {
    }

    public void showExamDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PracticesDownloadActivity.this);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("Try again", (dialog, id) -> {
                    mPresenter.dropData(getApplicationContext());
                    restartPMP();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
