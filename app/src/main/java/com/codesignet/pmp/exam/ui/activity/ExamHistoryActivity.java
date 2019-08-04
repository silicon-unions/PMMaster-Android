package com.codesignet.pmp.exam.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.exam.data_access_layer.database_manager.ExamDatabaseHelper;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionsItem;
import com.codesignet.pmp.exam.ui.adapter.QuestionAdapter;
import com.codesignet.pmp.exam.view.onSelectQuestion;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamHistoryActivity extends AppCompatActivity implements onSelectQuestion {
    @BindView(R.id.bmb)
    BoomMenuButton bmb;
    @BindView(R.id.recyclerView)
    RecyclerView questionList;
    @BindView(R.id.no_question)
    TextView error;

    private ArrayList<QuestionsItem> questionsList;
    private ExamDatabaseHelper databaseHelper;
    private QuestionAdapter questionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_history);
        ButterKnife.bind(this);
        questionsList = new ArrayList();
        databaseHelper = new ExamDatabaseHelper(getApplicationContext());
        String[] menuItems = getResources().getStringArray(R.array.menu_items);
        String[] menuItemsColors = getResources().getStringArray(R.array.menu_items_colors);

        InitializeMenu(menuItems,menuItemsColors);
        InitializeRecyclerView();
        if (databaseHelper.getAllQuestions().size() != 0) {
            error.setVisibility(View.GONE);
            questionList.setVisibility(View.VISIBLE);
            questionsList.addAll(databaseHelper.getAllQuestions());
        } else {
            error.setVisibility(View.VISIBLE);
            questionList.setVisibility(View.GONE);
        }
    }

    private void InitializeRecyclerView() {
        questionAdapter = new QuestionAdapter(questionsList, getApplicationContext(), this, "histroy");
        questionList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        questionList.setAdapter(questionAdapter);
        questionList.setHasFixedSize(true);
    }

    @Override
    public void showQuestionByNumber(int number) {
        Intent gotoreview = new Intent(getApplicationContext(), ExamReviewActivity.class);
        gotoreview.putExtra("ic_question_list", questionsList);
        gotoreview.putExtra("item", number);
        startActivity(gotoreview);
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
//                            AppRouter.navigateToExamScreen(getApplicationContext(), Constants.PRACTICE_TYPE);
//                            finish();
                            break;
                        case 2:
                            AppRouter.navigateToExamOptionScreen(getApplicationContext());
                            finish();
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

}
