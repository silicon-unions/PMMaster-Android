package com.codesignet.pmp.practices.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.exam.ui.adapter.StringAdapter;
import com.codesignet.pmp.exam.view.OnNavigateListener;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;
import com.codesignet.pmp.notes.ui.fragment.MyNotesScreenFragment;
import com.codesignet.pmp.notes.ui.fragment.QuestionNotesScreen;
import com.codesignet.pmp.practices.adapters.KnowledgeAreasAdapter;
import com.codesignet.pmp.practices.adapters.ProcessGroupsAdapter;
import com.codesignet.pmp.practices.data_access_layer.KnowledgeAreasObject;
import com.codesignet.pmp.practices.data_access_layer.ProcessGroupsObject;
import com.codesignet.pmp.practices.ui.fragments.KnowledgeAreasFragment;
import com.codesignet.pmp.practices.ui.fragments.ProcessGroupsFragment;
import com.codesignet.pmp.practices.view.OnPracticesTypeClicked;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.piotrek.customspinner.CustomSpinner;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class PracticeOptionActivity extends AppCompatActivity implements OnPracticesTypeClicked {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.bmb)
    BoomMenuButton bmb;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_practice_option);
        ButterKnife.bind(this);
        InitializeMenu();
        InitializeNavigation();
    }

    private void InitializeNavigation() {
        navigation.setOnNavigationItemSelectedListener
                (item -> {
                    switch (item.getItemId()) {
                        case R.id.action_item2:
                            selectedFragment = ProcessGroupsFragment.newInstance(this);
                            break;
                        case R.id.action_item1:
                            selectedFragment = KnowledgeAreasFragment.newInstance(this);
                            break;
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                    return true;
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        selectedFragment = KnowledgeAreasFragment.newInstance(this);
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();
    }

    @Override
    public void onKnowledgeAreasClicked(KnowledgeAreasObject object) {
        AppRouter.navigateToPracticesLevel(getApplicationContext(), object.getKnowledgeAreasName(), false);
    }

    @Override
    public void onProcessGroupsClicked(ProcessGroupsObject object) {
        AppRouter.navigateToPracticesLevel(getApplicationContext(), object.getProcessGroupsName(), true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            AppRouter.navigateToHomeScreen(getApplicationContext());
            finish();
        }
    }

    private void InitializeMenu() {
        String[] menuItemsColors = getResources().getStringArray(R.array.menu_items_colors);
        String[] menuItems = getResources().getStringArray(R.array.menu_items);
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
                            this.onBoomDidHide();
                            break;
                        case 2:
                            AppRouter.navigateToExamOptionScreen(getApplicationContext());
                            finish();
                            break;
                        case 3:
                            AppRouter.navigateToNoteScreen(getApplicationContext());
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
}
