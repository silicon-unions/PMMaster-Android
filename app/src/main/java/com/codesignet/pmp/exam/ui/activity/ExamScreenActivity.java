package com.codesignet.pmp.exam.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.app.Constants;
import com.codesignet.pmp.basics.BaseActivity;
import com.codesignet.pmp.exam.data_access_layer.ExamInteractor;
import com.codesignet.pmp.exam.data_access_layer.pojos.ExamData;
import com.codesignet.pmp.exam.data_access_layer.pojos.ExamOption;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionListResponse;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionsItem;
import com.codesignet.pmp.exam.dependencies.DaggerExamComponent;
import com.codesignet.pmp.exam.dependencies.ExamModule;
import com.codesignet.pmp.exam.presenter.ExamPresenter;
import com.codesignet.pmp.exam.ui.fragments.ExamFragment;
import com.codesignet.pmp.exam.ui.fragments.ExamQuestionListFragment;
import com.codesignet.pmp.exam.view.ExamView;
import com.codesignet.pmp.exam.view.OnNavigateListener;
import com.codesignet.pmp.exam.view.onSelectQuestion;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamScreenActivity extends BaseActivity<ExamInteractor, ExamView, ExamPresenter>
        implements OnNavigateListener, ExamView {

    @BindView(R.id.bmb)
    BoomMenuButton bmb;

    @Override
    public void onShowLoader() {

    }

    @Override
    public void onHideLoader() {

    }

    private String type;
    private ExamOption option;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        ButterKnife.bind(this);
        String[] menuItems = getResources().getStringArray(R.array.menu_items);
        String[] menuItemsColors = getResources().getStringArray(R.array.menu_items_colors);
        InitializeMenu(menuItems,menuItemsColors);
        navigateTo();
    }

    private void navigateTo() {
        Intent i = getIntent();
        option = (ExamOption) i.getSerializableExtra(Constants.EXAM_OPTIONS);
        navigateExamScreen(option);
    }

    private void InitializeMenu(String[] menuItems,String[] menuItemsColors) {
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalText(menuItems[i]).normalText(menuItems[i]).pieceColor(Color.parseColor(menuItemsColors[i])).normalColor(Color.parseColor(menuItemsColors[i]));
            bmb.setOnBoomListener(new OnBoomListener() {
                @Override
                public void onClicked(int index, BoomButton boomButton) {
                    switch (index) {
                        case 0:
                            AppRouter.navigateToHomeScreen(getApplicationContext());
                            finish();
                            break;
                        case 1:
                            AppRouter.navigateToPractices(getApplicationContext());
                            finish();
                            break;
                        case 2:
                            this.onBoomDidHide();
                            break;
                        case 3:
                            AppRouter.navigateToNoteScreen(getApplicationContext());
                            finish();
                            break;
                        case 4:
                            AppRouter.navigateToAskInstructorScreen(getApplicationContext());
                            finish();
                            break;
                        case 5:
                            AppRouter.navigateToProfileScreen(getApplicationContext());
                            finish();
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
    public void navigateExamScreen(ExamOption option) {
//        AppRouter.navigateToExamScreen();
//        bmb.setVisibility(View.GONE);
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.exam_container, ExamFragment.newInstance(this, option),"EXAM_FRAGMENT")
//                .commit();
    }

    @Override
    public void navigateTenStatisticsScreen(ExamData data) {

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            getSupportFragmentManager().popBackStack();
        else {
            finish();
        }
    }

    @Override
    public void onSuccess(QuestionListResponse response) {

    }

    @Override
    public void onFailure(Throwable e) {

    }
}
