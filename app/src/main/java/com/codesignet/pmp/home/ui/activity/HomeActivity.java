package com.codesignet.pmp.home.ui.activity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.codesignet.pmp.BuildConfig;
import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.app.Constants;
import com.codesignet.pmp.basics.BaseActivity;
import com.codesignet.pmp.exam.view.OnNavigateListener;
import com.codesignet.pmp.home.data_access_layer.HomeInteractor;
import com.codesignet.pmp.home.dependencies.DaggerHomeComponent;
import com.codesignet.pmp.home.dependencies.HomeModule;
import com.codesignet.pmp.home.presenter.HomePresenter;
import com.codesignet.pmp.home.ui.fragments.HomeFragment;
import com.codesignet.pmp.home.view.HomeView;
import com.codesignet.pmp.practices.data_access_layer.pojos.Levels;
import com.codesignet.pmp.practices.data_access_layer.pojos.UserLevels;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kartikarora.potato.Potato;

public class HomeActivity extends BaseActivity<HomeInteractor, HomeView, HomePresenter>
        implements HomeView {


    @BindView(R.id.container)
    FrameLayout exam_container;

    @BindView(R.id.bmb)
    BoomMenuButton bmb;

    private AlertDialog alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitializeDagger();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        String[] menuItems = getResources().getStringArray(R.array.menu_items);
        String[] menuItemsColors = getResources().getStringArray(R.array.menu_items_colors);
        InitializeMenu(menuItems, menuItemsColors);
        loadAndAddToBackStackFragment(R.id.container, HomeFragment.newInstance(this));
        checkConnection();
        SharedPreferences.Editor editor= getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).edit();
        editor.putString(Constants.Project_integration_management, "1");

    }

    private void checkConnection() {
        if (Potato.potate(getApplicationContext()).Utils().isInternetConnected()) {
            mPresenter.syncAllNotes(getAccessToken(), getApplicationContext());
            if(BuildConfig.FLAVOR.equals("Paid")){
                mPresenter.syncUserLevel(getAccessToken(), updateUserLevel());
            }
        } else {
            mPresenter.getAllNotesFromAPI(getApplicationContext());
        }
    }

    private void InitializeDagger() {
        DaggerHomeComponent
                .builder()
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    private void InitializeMenu(String[] menuItems, String[] menuItemsColors) {
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalText(menuItems[i]).pieceColor(Color.parseColor(menuItemsColors[i])).normalColor(Color.parseColor(menuItemsColors[i]));

            bmb.setOnBoomListener(new OnBoomListener() {
                @Override
                public void onClicked(int index, BoomButton boomButton) {
                    switch (index) {
                        case 0:
                            this.onBoomDidHide();
                            break;
                        case 1:
                            AppRouter.navigateToPractices(getApplicationContext());
                            break;
                        case 2:
                            AppRouter.navigateToExamOptionScreen(getApplicationContext());
                            break;
                        case 3:
                            AppRouter.navigateToNoteScreen(getApplicationContext());
                            break;
                        case 4:
                            AppRouter.navigateToAskInstructorScreen(getApplicationContext());
                            break;
                        case 5:
                            AppRouter.navigateToProfileScreen(getApplicationContext());
                            break;
                    }
                }

                @Override
                public void onBackgroundClick() {

                }

                @Override
                public void onBoomWillHide() {

                }

                @Override
                public void onBoomDidHide() {

                }

                @Override
                public void onBoomWillShow() {

                }

                @Override
                public void onBoomDidShow() {

                }
            });
            bmb.addBuilder(builder);
            bmb.setAutoBoom(false);
            bmb.setNormalColor(getResources().getColor(R.color.off_white));
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            getSupportFragmentManager().popBackStack();
        else
            showExamDialog("Exit","Are you sure you want to exit?",false);
    }

    public void showExamDialog(String title, String message, boolean hideNoButtom) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("Exit", (dialog, id) -> {
                    finish();

                });
        if(!hideNoButtom) {
            builder.setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
        }
        alert = builder.create();
        alert.show();
    }

    private UserLevels updateUserLevel() {
        UserLevels userLevels = new UserLevels();
        SharedPreferences prefs = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        Levels levels = new Levels();

        levels.setInitiatingProcessGroup(prefs.getString(Constants.Initiating_Process_Group, "0"));
        levels.setPlanningProcessGroup(prefs.getString(Constants.Planning_Process_Group, "0"));
        levels.setExecutingProcessGroup(prefs.getString(Constants.Executing_Process_Group, "0"));
        levels.setMonitoringAndControllingProcessGroup(prefs.getString(Constants.Monitoring_and_Controlling_Process_Group, "0"));
        levels.setClosingProcessGroup(prefs.getString(Constants.Closing_Process_Group, "0"));
        levels.setProjectIntegrationManagement(prefs.getString(Constants.Project_integration_management, "0"));
        levels.setProjectScopeManagement(prefs.getString(Constants.Project_scope_management, "0"));
        levels.setProjectScheduleManagement(prefs.getString(Constants.Project_schedule_management, "0"));
        levels.setProjectCostManagement(prefs.getString(Constants.Project_cost_management, "0"));
        levels.setProjectQualityManagement(prefs.getString(Constants.Project_quality_management, "0"));
        levels.setProjectResourceManagement(prefs.getString(Constants.Project_resource_management, "0"));
        levels.setProjectCommunicationsManagement(prefs.getString(Constants.Project_communications_management, "0"));
        levels.setProjectRiskManagement(prefs.getString(Constants.Project_risk_management, "0"));
        levels.setProjectProcurementManagement(prefs.getString(Constants.Project_procurement_management, "0"));
        levels.setProjectStakeholderManagement(prefs.getString(Constants.Project_stakeholder_management, "0"));
        userLevels.setLevels(levels);
        return userLevels;
    }

    @Override
    public void navigateTenQuestionScreen() {
        AppRouter.navigateToPracticesActivity(getApplicationContext());
    }

    @Override
    public void navigateToTodayExamDataScreen() {
        AppRouter.navigateTodayExamDataActivity(getApplicationContext());
    }

    @Override
    public void navigateToLastExamScreen() {
        AppRouter.navigateToLastExam(getApplicationContext());
    }
}
