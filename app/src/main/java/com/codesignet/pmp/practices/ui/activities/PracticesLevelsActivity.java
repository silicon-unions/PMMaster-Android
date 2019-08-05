package com.codesignet.pmp.practices.ui.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.codesignet.pmp.BuildConfig;
import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.app.Constants;
import com.codesignet.pmp.basics.BaseActivity;
import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.home.ui.activity.HomeActivity;
import com.codesignet.pmp.practices.adapters.LevelAdapter;
import com.codesignet.pmp.practices.data_access_layer.PracticesInteractor;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesListResponse;
import com.codesignet.pmp.practices.data_access_layer.pojos.UserLevels;
import com.codesignet.pmp.practices.dependencies.DaggerPracticesComponent;
import com.codesignet.pmp.practices.dependencies.PracticesModule;
import com.codesignet.pmp.practices.presenter.PracticesPresenter;
import com.codesignet.pmp.practices.view.PracticesView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PracticesLevelsActivity extends BaseActivity<PracticesInteractor, PracticesView, PracticesPresenter> implements PracticesView {

    @BindView(R.id.rv_level)
    RecyclerView levels;

    @BindView(R.id.levels_title)
    TextView title;

    private RecyclerView.LayoutManager mLayoutManager;
    private LevelAdapter levelAdapter;
    private ArrayList<String> levelsList;
    private String levelData;
    private String user_selection;
    private float curentLeveData;
    private boolean isProcessGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitializeDagger();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practices_levels);
        ButterKnife.bind(this);
        Intent i = getIntent();
        user_selection = i.getStringExtra("user_selection");
        isProcessGroup = i.getBooleanExtra("is_process_group", false);
        SharedPreferences prefs = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        levelData = prefs.getString(user_selection, "0");
        title.setText(user_selection);
        InitializeLevelListData();
        InitializeRecyclerView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        levelData = prefs.getString(user_selection, "0");
        curentLeveData = mPresenter.getSolvedQuestionNumber(isProcessGroup,user_selection,levelData,getApplicationContext());
        levelAdapter.notifyDataSetChanged();
        InitializeLevelListData();
        InitializeRecyclerView();
        title.setText(user_selection);
    }

    private void InitializeDagger() {
        DaggerPracticesComponent
                .builder()
                .practicesModule(new PracticesModule(this))
                .build()
                .inject(this);
    }

    private void InitializeRecyclerView() {
        levels.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        levels.setLayoutManager(mLayoutManager);
        levels.setAdapter(levelAdapter);
    }

    private void InitializeLevelListData() {
        levelsList = new ArrayList();
        levelsList.addAll(Arrays.asList(getResources().getStringArray(R.array.levels)));
        levelAdapter = new LevelAdapter(this, levelsList, levelData, curentLeveData);
    }

    @Override
    public void onSuccess(PracticesListResponse response) {
    }

    @Override
    public void onSuccess(BaseResponse response) {
    }

    @Override
    public void onFailure(Throwable e) {
    }

    @Override
    public void onLevelsReceived(UserLevels userLevels) {
    }

    @Override
    public void onLevelClicked(int level) {
        int currentLevel = Integer.valueOf(levelData);
        if (currentLevel == level) {
            if  (BuildConfig.FLAVOR.equals("Free") && level != 1) {
                showExamDialog("Upgrade", "Upgrade to have access to full version", false);
            }
            else {
                AppRouter.navigateToPracticesActivity(getApplicationContext(), currentLevel, user_selection, isProcessGroup);
            }
        }
        if (currentLevel > level) {
            showExamDialog("Congratulations", "You have successfully completed this Level questions ");
        }
        if (currentLevel < level) {
            if (BuildConfig.FLAVOR.equals("Paid")) {
                showExamDialog(null, "You have to finish the previews levels to be able to access this level");
            } else {
                showExamDialog("Upgrade", "Upgrade to have access to full version", false);
            }
        }
    }

    public void showExamDialog(String title, String message, boolean hideNoButtom) {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object

        AlertDialog.Builder builder = new AlertDialog.Builder(PracticesLevelsActivity.this);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("Upgrade", (dialog, id) -> {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    finish();
                });
        if (!hideNoButtom) {
            builder.setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
        }
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showExamDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PracticesLevelsActivity.this);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
