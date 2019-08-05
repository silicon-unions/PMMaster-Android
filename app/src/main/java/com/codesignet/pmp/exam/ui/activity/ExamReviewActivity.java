package com.codesignet.pmp.exam.ui.activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionsItem;
import com.codesignet.pmp.practices.ui.activities.PracticesActivity;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExamReviewActivity extends AppCompatActivity {

    @BindView(R.id.bmb)
    BoomMenuButton bmb;
    @BindView(R.id.tv_question)
    TextView question;
    @BindView(R.id.tv_answer_one)
    TextView answerOne;
    @BindView(R.id.tv_answer_two)
    TextView answerTwo;
    @BindView(R.id.tv_answer_three)
    TextView answerThree;
    @BindView(R.id.tv_answer_four)
    TextView answerFour;
    @BindView(R.id.answer_view_one)
    View viewOne;
    @BindView(R.id.answer_view_two)
    View viewTwo;
    @BindView(R.id.answer_view_three)
    View viewThree;
    @BindView(R.id.answer_view_four)
    View viewFour;
    @BindView(R.id.iv_next_question)
    Button nextQuestion;
    @BindView(R.id.iv_previous_question)
    Button previousQuestion;
    @BindView(R.id.iv_translate_question)
    ImageView translateQuestion;
    @BindView(R.id.mother)
    ConstraintLayout mother;
    @BindView(R.id.iv_answer_one)
    ImageView iOne;
    @BindView(R.id.iv_answer_two)
    ImageView iTwo;
    @BindView(R.id.iv_answer_three)
    ImageView iThree;
    @BindView(R.id.iv_answer_four)
    ImageView iFour;

    private ArrayList<QuestionsItem> questionsList;
    private boolean local = false;
    private int currentQuestion;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_review);
        ButterKnife.bind(this);
        String[] menuItems = getResources().getStringArray(R.array.menu_items);
        String[] menuItemsColors = getResources().getStringArray(R.array.menu_items_colors);

        InitializeMenu(menuItems,menuItemsColors);
        questionsList = new ArrayList();
        previousQuestion.setOnClickListener(v -> loadPreviousQuestion());
        nextQuestion.setOnClickListener(v -> loadNextQuestion());
        translateQuestion.setOnClickListener(v -> translate());

        questionsList = (ArrayList<QuestionsItem>) getIntent().getSerializableExtra("ic_question_list");
        currentQuestion = getIntent().getIntExtra("item", 0);
        showQuestion(currentQuestion);
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

    public void translate() {
        local = !local;
        showQuestion(currentQuestion);
    }

    public void loadPreviousQuestion() {
        currentQuestion -= 1;
        if (currentQuestion < 0)
            currentQuestion = 0;
        showQuestion(currentQuestion);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void loadNextQuestion() {
        if (currentQuestion < questionsList.size() - 1) {
            currentQuestion += 1;
            showQuestion(currentQuestion);
        }
    }

    private void showQuestion(int item) {
        if (local) {
            question.setText(questionsList.get(item).getQuestionA());
            answerOne.setText(questionsList.get(item).getAnswerA1());
            answerTwo.setText(questionsList.get(item).getAnswerA2());
            answerThree.setText(questionsList.get(item).getAnswerA3());
            answerFour.setText(questionsList.get(item).getAnswerA4());
        } else {
            question.setText(questionsList.get(item).getQuestionE());
            answerOne.setText(questionsList.get(item).getAnswerE1());
            answerTwo.setText(questionsList.get(item).getAnswerE2());
            answerThree.setText(questionsList.get(item).getAnswerE3());
            answerFour.setText(questionsList.get(item).getAnswerE4());
        }
        retrieveQuestionData(item);
    }

    public void retrieveQuestionData(int questionNumber) {
        if (questionsList.get(questionNumber).getUserAnswer() == null) {
            showAnswer(7);
            showCorrectAnswer(Integer.parseInt(questionsList.get(questionNumber).getAnswer()));

        } else {
            showCorrectAnswer(Integer.parseInt(questionsList.get(questionNumber).getAnswer()));
            showJustfication(Integer.parseInt(questionsList.get(questionNumber).getAnswer()));
            if (Integer.parseInt(questionsList.get(questionNumber).getUserAnswer())
                    != Integer.parseInt(questionsList.get(questionNumber).getAnswer()))
                showWrongAnswer(Integer.parseInt(questionsList.get(questionNumber).getUserAnswer()));
        }
    }

    private void showAnswer(int position) {
        switch (position) {
            case 1:
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.GONE);
                viewThree.setVisibility(View.GONE);
                viewFour.setVisibility(View.GONE);
                break;
            case 2:
                viewOne.setVisibility(View.GONE);
                viewOne.setBackgroundColor(getResources().getColor(R.color.green));
                viewTwo.setVisibility(View.VISIBLE);
                viewThree.setVisibility(View.GONE);
                viewFour.setVisibility(View.GONE);
                break;
            case 3:
                viewOne.setVisibility(View.GONE);
                viewOne.setBackgroundColor(getResources().getColor(R.color.green));
                viewTwo.setVisibility(View.GONE);
                viewThree.setVisibility(View.VISIBLE);
                viewFour.setVisibility(View.GONE);
                break;
            case 4:
                viewOne.setVisibility(View.GONE);
                viewOne.setBackgroundColor(getResources().getColor(R.color.green));
                viewTwo.setVisibility(View.GONE);
                viewThree.setVisibility(View.GONE);
                viewFour.setVisibility(View.VISIBLE);
                break;
            default:
                viewOne.setVisibility(View.GONE);
                viewTwo.setVisibility(View.GONE);
                viewThree.setVisibility(View.GONE);
                viewFour.setVisibility(View.GONE);
                break;
        }
    }

    private void showCorrectAnswer(int answer) {
        switch (answer) {
            case 1:
                viewOne.setBackgroundColor(getResources().getColor(R.color.green));
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.GONE);
                viewThree.setVisibility(View.GONE);
                viewFour.setVisibility(View.GONE);
                break;
            case 2:
                viewTwo.setBackgroundColor(getResources().getColor(R.color.green));
                viewTwo.setVisibility(View.VISIBLE);
                viewOne.setVisibility(View.GONE);
                viewThree.setVisibility(View.GONE);
                viewFour.setVisibility(View.GONE);
                break;
            case 3:
                viewThree.setBackgroundColor(getResources().getColor(R.color.green));
                viewThree.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.GONE);
                viewOne.setVisibility(View.GONE);
                viewFour.setVisibility(View.GONE);
                break;
            case 4:
                viewFour.setBackgroundColor(getResources().getColor(R.color.green));
                viewOne.setVisibility(View.GONE);
                viewTwo.setVisibility(View.GONE);
                viewThree.setVisibility(View.GONE);
                viewFour.setVisibility(View.VISIBLE);
                break;
            default:
                viewOne.setVisibility(View.GONE);
                viewTwo.setVisibility(View.GONE);
                viewThree.setVisibility(View.GONE);
                viewFour.setVisibility(View.GONE);
                break;
        }
    }

    private void showWrongAnswer(int wrongAnswer) {
        switch (wrongAnswer) {
            case 1:
                viewOne.setBackgroundColor(getResources().getColor(R.color.orange_pink));
                viewOne.setVisibility(View.VISIBLE);

                break;
            case 2:
                viewTwo.setBackgroundColor(getResources().getColor(R.color.orange_pink));
                viewTwo.setVisibility(View.VISIBLE);

                break;
            case 3:
                viewThree.setBackgroundColor(getResources().getColor(R.color.orange_pink));
                viewThree.setVisibility(View.VISIBLE);

                break;
            case 4:
                viewFour.setBackgroundColor(getResources().getColor(R.color.orange_pink));
                viewFour.setVisibility(View.VISIBLE);

                break;
            default:
                viewOne.setVisibility(View.GONE);
                viewTwo.setVisibility(View.GONE);
                viewThree.setVisibility(View.GONE);
                viewFour.setVisibility(View.GONE);
                break;
        }
    }
    private void showJustfication(int answer){
        switch (answer) {
            case 1:
                iOne.setVisibility(View.VISIBLE);
                iTwo.setVisibility(View.INVISIBLE);
                iThree.setVisibility(View.INVISIBLE);
                iFour.setVisibility(View.INVISIBLE);
                break;
            case 2:
                iTwo.setVisibility(View.VISIBLE);
                iOne.setVisibility(View.INVISIBLE);
                iThree.setVisibility(View.INVISIBLE);
                iFour.setVisibility(View.INVISIBLE);
                break;
            case 3:
                iOne.setVisibility(View.INVISIBLE);
                iTwo.setVisibility(View.INVISIBLE);
                iFour.setVisibility(View.INVISIBLE);
                iThree.setVisibility(View.VISIBLE);

                break;
            case 4:
                iOne.setVisibility(View.INVISIBLE);
                iTwo.setVisibility(View.INVISIBLE);
                iThree.setVisibility(View.INVISIBLE);
                iFour.setVisibility(View.VISIBLE);

                break;
            default:
                iOne.setVisibility(View.INVISIBLE);
                iTwo.setVisibility(View.INVISIBLE);
                iThree.setVisibility(View.INVISIBLE);
                iFour.setVisibility(View.INVISIBLE);
                break;
        }
    }
    public void justification() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExamReviewActivity.this);
        String message;
        if(local){
            message = questionsList.get(currentQuestion).getJustificationA();
        }
        else {
            message = questionsList.get(currentQuestion).getJustificationE();
        }
        builder.setMessage(message)
                .setTitle("Justification")
                .setCancelable(false)
                .setNegativeButton("Ok", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
    @OnClick(R.id.iv_answer_one)
    public void justificationOnelicked() {
        justification();
    }

    @OnClick(R.id.iv_answer_two)
    public void justificationTwoClicked() {
        justification();
    }

    @OnClick(R.id.iv_answer_three)
    public void justificationThreeClicked() {
        justification();
    }

    @OnClick(R.id.iv_answer_four)
    public void justificationFourClicked() {
        justification();
    }
}
