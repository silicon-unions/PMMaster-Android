package com.codesignet.pmp.practices.ui.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.app.Constants;
import com.codesignet.pmp.basics.BaseActivity;
import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.exam.data_access_layer.pojos.ExamData;
import com.codesignet.pmp.exam.data_access_layer.pojos.ReportObject;
import com.codesignet.pmp.home.data_access_layer.ToadyExamDatabaseHelper;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;
import com.codesignet.pmp.practices.data_access_layer.PracticesInteractor;
import com.codesignet.pmp.practices.data_access_layer.database_manager.PracticesDatabaseHelper;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesListResponse;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesQuestionsItem;
import com.codesignet.pmp.practices.data_access_layer.pojos.UserLevels;
import com.codesignet.pmp.practices.dependencies.DaggerPracticesComponent;
import com.codesignet.pmp.practices.dependencies.PracticesModule;
import com.codesignet.pmp.practices.presenter.PracticesPresenter;
import com.codesignet.pmp.practices.view.PracticesView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PracticesActivity extends BaseActivity<PracticesInteractor, PracticesView, PracticesPresenter>
        implements PracticesView {

    private static final String FORMAT = "%02d:%02d:%02d";
    @BindView(R.id.tv_question)
    TextView question;
    @BindView(R.id.iv_answer_one)
    ImageView iOne;
    @BindView(R.id.iv_answer_two)
    ImageView iTwo;
    @BindView(R.id.iv_answer_three)
    ImageView iThree;
    @BindView(R.id.iv_answer_four)
    ImageView iFour;
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
    @BindView(R.id.iv_report_question)
    ImageView reportQuestion;
    @BindView(R.id.iv_translate_question)
    ImageView translateQuestion;
    @BindView(R.id.iv_note_question)
    ImageView noteQuestion;
    @BindView(R.id.iv_next_question)
    Button nextQuestion;
    @BindView(R.id.iv_previous_question)
    Button previousQuestion;
    @BindView(R.id.iv_ten_next_question)
    Button iv_ten_next_question;

    private boolean local = false;
    private boolean tenQuestionFlag;
    private int correctAnswers = 0;
    private int tenQuestionCount = 0;
    private int wrongAnswerCount = 0;
    private int unresolvedCount = 0;
    private String user_selection;
    private boolean isProcessGroup;
    private String level;
    private PracticesDatabaseHelper databaseHelper;
    private PracticesQuestionsItem questionData;
    private SharedPreferences.Editor editor;
    private Boolean islastanswerCorrect = null;

    private LinkedList<PracticesQuestionsItem> questionList;
    private int questionlimit = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitializeDagger();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practices);
        ButterKnife.bind(this);
        databaseHelper = new PracticesDatabaseHelper(getApplicationContext());
        translateQuestion.setOnClickListener(v -> translate());
        nextQuestion.setOnClickListener(v -> loadNextQuestion());
        iv_ten_next_question.setOnClickListener(v -> loadNextQuestion());
        noteQuestion.setOnClickListener(v -> onNoteClicked());
        reportQuestion.setOnClickListener(v -> onReportClicked());
        previousQuestion.setOnClickListener(v -> loadPreviousQuestion());
        Intent i = getIntent();
        questionList = new LinkedList();
        user_selection = i.getStringExtra("user_selection");
        isProcessGroup = i.getBooleanExtra("is_process_group", false);
        level = String.valueOf(i.getIntExtra("currentLevel", 0));
        editor = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).edit();
        tenQuestionFlag = i.getBooleanExtra("ten_question", false);
        if (tenQuestionFlag) {
            iv_ten_next_question.setVisibility(View.VISIBLE);
            nextQuestion.setVisibility(View.INVISIBLE);
            previousQuestion.setVisibility(View.INVISIBLE);
            questionData = mPresenter.getQuestion(getApplicationContext());
            showQuestion(questionData);
            tenQuestionCount++;
        } else {
            iv_ten_next_question.setVisibility(View.INVISIBLE);
            nextQuestion.setVisibility(View.VISIBLE);
            previousQuestion.setVisibility(View.VISIBLE);
            questionData = mPresenter.getNextQuestionFromData(isProcessGroup, user_selection, level, 0, getApplicationContext());
            if (questionData == null) {
                showExamDialog("Congratulations", "You have Successfully Complited this Level questions ");
            } else {
                showQuestion(questionData);
            }
        }
    }

    private void loadPreviousQuestion() {
        if (questionList.size() != 0) {
            if (questionlimit != 0) {
                questionlimit--;
                questionData = questionList.get(questionlimit);
                showQuestion(questionData);
                hideAllAnswer();
                checkAnswer(Integer.parseInt(questionData.getUserAnswer()));
                Log.i("Hello", "loadNextQuestion + loadPreviousQuestion \t limit" + questionlimit + "\t size" + questionList.size() + "\t question answer is " + questionData.getUserAnswer());
                enableView(false);
            }
        }
    }

    private void InitializeDagger() {
        DaggerPracticesComponent
                .builder()
                .practicesModule(new PracticesModule(this))
                .build()
                .inject(this);
    }

    // call-back
    @Override
    public void onSuccess(PracticesListResponse response) {
    }

    @Override
    public void onFailure(Throwable e) {
        onError(e);
    }

    @Override
    public void onSuccess(BaseResponse response) {
    }

    @Override
    public void onLevelClicked(int level) {
    }

    @Override
    public void onLevelsReceived(UserLevels userLevels) {
    }

    public void translate() {
        local = !local;
        showQuestion(questionData);
    }

    public void loadNextQuestion() {
        if (tenQuestionFlag) {
            iv_ten_next_question.setVisibility(View.VISIBLE);
            nextQuestion.setVisibility(View.INVISIBLE);
            previousQuestion.setVisibility(View.INVISIBLE);
            if (tenQuestionCount < 10) {
                questionData = mPresenter.getQuestion(getApplicationContext());
                showQuestion(questionData);
                tenQuestionCount++;
                enableView(true);
                hideAllAnswer();
            } else {
                ExamData examData = new ExamData();
                examData.setCorrectAnswers(correctAnswers);
                examData.setUnSolvedQuestions(10 - (correctAnswers + wrongAnswerCount));
                examData.setWrongAnswers(wrongAnswerCount);
                examData.setTenQuestionType(true);
                AppRouter.navigateTenStatisticsScreen(getApplicationContext(), examData);
                finish();
            }
        } else {
            iv_ten_next_question.setVisibility(View.INVISIBLE);
            nextQuestion.setVisibility(View.VISIBLE);
            previousQuestion.setVisibility(View.VISIBLE);
            ToadyExamDatabaseHelper databaseHelper = new ToadyExamDatabaseHelper(getApplicationContext());
            if (islastanswerCorrect != null) {
                if (islastanswerCorrect) {
                    databaseHelper.updateTodayExamData(convertLongDataToString(System.currentTimeMillis()), 1, 0);
                } else {
                    databaseHelper.updateTodayExamData(convertLongDataToString(System.currentTimeMillis()), 0, 1);
                }
            }

            if (questionlimit <= questionList.size() - 1) {
                Log.i("Hello", "questionlimit is " + questionlimit + "\t questionList.size()" + questionList.size());
                questionData = questionList.get(questionlimit);
                showQuestion(questionData);
                hideAllAnswer();
                checkAnswer(Integer.parseInt(questionData.getUserAnswer()));
                questionlimit++;
                enableView(false);
            } else {
                if (questionlimit != 20) {
                    questionlimit++;
                    questionList.add(questionData);
                    Log.i("Hello", "loadNextQuestion + loadPreviousQuestion \t limit" + questionlimit + "\t size" + questionList.size() + "\t question answer is " + questionData.getUserAnswer());
                } else {
                    questionList.removeFirst();
                    questionList.add(questionData);
                    Log.i("Hello", "loadNextQuestion + loadPreviousQuestion \t limit" + questionlimit + "\t size" + questionList.size());
                }
                questionData = mPresenter.getNextQuestionFromData(isProcessGroup, user_selection, level, questionData.getId(), getApplicationContext());
                if (questionData == null) {
                    showExamDialog("Congratulations", "You have successfully completed this Level questions ");
                } else {
                    Log.i("XXX", questionData.getAnswer());
                    showQuestion(questionData);
                    enableView(true);
                    hideAllAnswer();
                }
            }
        }
    }

    private String convertLongDataToString(long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    public void onNoteClicked() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PracticesActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.view_note_layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        Note note = new Note();
        EditText noteData = dialogView.findViewById(R.id.et_message);
        dialogBuilder.setTitle(getApplicationContext().getResources().getString(R.string.add_new_question_note_title));
        dialogBuilder.setPositiveButton(getApplicationContext().getResources().getString(R.string.add), (dialog, whichButton) -> {
            note.setNote(noteData.getText().toString());
            note.setUpdatedAt(getCurrentTime());
            note.setType("question");
            mPresenter.addNewNoteToAPI(note, questionData, getApplicationContext());
        });
        dialogBuilder.setNegativeButton(getApplicationContext().getResources().getString(R.string.cancel), (dialog, whichButton) -> {
            dialog.dismiss();
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void onReportClicked() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PracticesActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.view_report_layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        ReportObject obj = new ReportObject();
        EditText noteData = dialogView.findViewById(R.id.et_message);
        dialogBuilder.setTitle(getApplicationContext().getResources().getString(R.string.report_title));
        dialogBuilder.setPositiveButton(getApplicationContext().getResources().getString(R.string.add), (dialog, whichButton) -> {
            obj.setMessage(noteData.getText().toString());
            obj.setQuestion_id(String.valueOf(questionData.getId()));
            mPresenter.reportQuestion(obj, getAccessToken());
        });
        dialogBuilder.setNegativeButton(getApplicationContext().getResources().getString(R.string.cancel), (dialog, whichButton) -> {
            dialog.dismiss();
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @OnClick(R.id.tv_answer_one)
    public void answerOneClicked() {
        checkAnswer(1);
    }

    @OnClick(R.id.tv_answer_two)
    public void answerTwoClicked() {
        checkAnswer(2);
    }

    @OnClick(R.id.tv_answer_three)
    public void answerThreeClicked() {
        checkAnswer(3);
    }

    @OnClick(R.id.tv_answer_four)
    public void answerFourClicked() {
        checkAnswer(4);
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

    private void checkAnswer(int answer) {
        if (answer != 0) {
            questionData.setUserAnswer(String.valueOf(answer));
            setAnswerColor(Integer.valueOf(questionData.getAnswer()), getResources().getColor(R.color.green));
            showJustfication(Integer.valueOf(questionData.getAnswer()));
            enableView(false);
            questionData.setUserAnswer(String.valueOf(answer));
            if (!mPresenter.updateUserAnswer(questionData, getApplicationContext()))
                showMessage("Something went Wrong ");

            if (answer != Integer.valueOf(questionData.getAnswer())) {
                setAnswerColor(answer, getResources().getColor(R.color.orange_pink));
                islastanswerCorrect = false;
                wrongAnswerCount++;
            }
            if (answer == Integer.valueOf(questionData.getAnswer())) {
                islastanswerCorrect = true;
                correctAnswers++;
            }
        } else {
            Log.i("Hello", "answer is null");
        }
    }

    private void enableView(boolean status) {
        answerFour.setClickable(status);
        answerThree.setClickable(status);
        answerTwo.setClickable(status);
        answerOne.setClickable(status);
    }

    private void showQuestion(PracticesQuestionsItem item) {
        if (local) {
            question.setText(item.getQuestionA());
            answerOne.setText(item.getAnswerA1());
            answerTwo.setText(item.getAnswerA2());
            answerThree.setText(item.getAnswerA3());
            answerFour.setText(item.getAnswerA4());
        } else {
            question.setText(item.getQuestionE());
            answerOne.setText(item.getAnswerE1());
            answerTwo.setText(item.getAnswerE2());
            answerThree.setText(item.getAnswerE3());
            answerFour.setText(item.getAnswerE4());
        }
    }

    public void showExamDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PracticesActivity.this);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("submit", (dialog, id) -> {
                    editor.putString(user_selection, String.valueOf(Integer.valueOf(level) + 1));
                    editor.apply();
                    finish();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private String getCurrentTime() {
        String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    private void setAnswerColor(int answer, int color) {
        switch (answer) {
            case 1:
                viewOne.setBackgroundColor(color);
                viewOne.setVisibility(View.VISIBLE);
                break;
            case 2:
                viewTwo.setBackgroundColor(color);
                viewTwo.setVisibility(View.VISIBLE);
                break;
            case 3:
                viewThree.setBackgroundColor(color);
                viewThree.setVisibility(View.VISIBLE);

                break;
            case 4:
                viewFour.setBackgroundColor(color);
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

    private void showJustfication(int answer) {
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

    private void hideAllAnswer() {
        viewOne.setVisibility(View.GONE);
        viewTwo.setVisibility(View.GONE);
        viewThree.setVisibility(View.GONE);
        viewFour.setVisibility(View.GONE);
        iOne.setVisibility(View.GONE);
        iTwo.setVisibility(View.GONE);
        iThree.setVisibility(View.GONE);
        iFour.setVisibility(View.GONE);
    }

    public void justification() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PracticesActivity.this);
        String message;
        if (local) {
            message = questionData.getJustificationA();
        } else {
            message = questionData.getJustificationE();
        }
        builder.setMessage(message)
                .setTitle("Justification")
                .setCancelable(false)
                .setNegativeButton("Ok", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
}