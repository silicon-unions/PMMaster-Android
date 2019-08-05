package com.codesignet.pmp.exam.ui.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.app.Constants;
import com.codesignet.pmp.basics.BaseActivity;
import com.codesignet.pmp.basics.BaseFragment;
import com.codesignet.pmp.exam.data_access_layer.ExamInteractor;
import com.codesignet.pmp.exam.data_access_layer.database_manager.ExamDatabaseHelper;
import com.codesignet.pmp.exam.data_access_layer.pojos.ExamData;
import com.codesignet.pmp.exam.data_access_layer.pojos.ExamObject;
import com.codesignet.pmp.exam.data_access_layer.pojos.ExamOption;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionListResponse;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionsItem;
import com.codesignet.pmp.exam.data_access_layer.pojos.ReportObject;
import com.codesignet.pmp.exam.dependencies.DaggerExamComponent;
import com.codesignet.pmp.exam.dependencies.ExamModule;
import com.codesignet.pmp.exam.presenter.ExamPresenter;
import com.codesignet.pmp.exam.ui.adapter.QuestionAdapter;
import com.codesignet.pmp.exam.view.ExamView;
import com.codesignet.pmp.exam.view.OnNavigateListener;
import com.codesignet.pmp.exam.view.onSelectQuestion;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExamFragment extends BaseActivity<ExamInteractor, ExamView, ExamPresenter>
        implements ExamView, onSelectQuestion {

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

    @BindView(R.id.iv_mark_question)
    ImageView markQuestion;
    @BindView(R.id.answer_view_four)
    View viewFour;
    @BindView(R.id.iv_translate_question)
    ImageView translateQuestion;
    @BindView(R.id.iv_list_question)
    ImageView listQuestion;
    @BindView(R.id.iv_note_question)
    ImageView noteQuestion;
    @BindView(R.id.iv_next_question)
    Button nextQuestion;
    @BindView(R.id.iv_previous_question)
    Button previousQuestion;
    @BindView(R.id.iv_report_question)
    ImageView reportQuestion;
    @BindView(R.id.pb_loading)
    ProgressBar loading;

    private OnNavigateListener callback;
    private boolean local = false;
    private int currentQuestion;
    private ArrayList<QuestionsItem> questionsList;
    private ExamOption option;
    @BindView(R.id.tv_exam_counter)
    TextView questionCounter;
    private int correctAnswers;
    private int wrongAnswers;
    private int flaggedQuestions;
    private int unSolvedQuestions;
    private ExamDatabaseHelper databaseHelper;
    private long currentQuestionTime;
    private long questionTime;
    private CountDownTimer countDownTimer;
    private AlertDialog questionsListDialog;
    private AlertDialog alert;
    private AlertDialog.Builder builder;

//    public static Fragment newInstance(OnNavigateListener callback, ExamOption option) {
//        ExamFragment examFragment = new ExamFragment();
//        examFragment.callback = callback;
//        examFragment.option = option;
//        return examFragment;
//    }

    @Override
    public void onShowLoader() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoader() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        InitializeDagger();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_exam);
        ButterKnife.bind(this);
        questionsList = new ArrayList();
        navigateTo();
        builder = new AlertDialog.Builder(this);
        alert = builder.create();
        databaseHelper = new ExamDatabaseHelper(this);
        mPresenter.getQuestionList(getAccessToken(), new ExamObject(option.getQuestionNumber()));
        previousQuestion.setOnClickListener(v -> loadPreviousQuestion());
        nextQuestion.setOnClickListener(v -> loadNextQuestion());
        translateQuestion.setOnClickListener(v -> translate());
        noteQuestion.setOnClickListener(v -> onNoteClicked());
        markQuestion.setOnClickListener(v -> onFlagClicked());
        listQuestion.setOnClickListener(v -> onListClicked());
        reportQuestion.setOnClickListener(v -> onReportClicked());
    }

    private void navigateTo() {
        Intent i = getIntent();
        option = (ExamOption) i.getSerializableExtra(Constants.EXAM_OPTIONS);
    }

    private void InitializeDagger() {
        DaggerExamComponent
                .builder()
                .examModule(new ExamModule(this))
                .build()
                .inject(this);
    }

    private void onFlagClicked() {
        if (questionsList.get(currentQuestion).isFlag()) {
            questionsList.get(currentQuestion).setFlag(false);
            markQuestion.setImageResource(R.drawable.ic_unselected_flag);
        } else {
            questionsList.get(currentQuestion).setFlag(true);
            markQuestion.setImageResource(R.drawable.ic_selected_flag);
        }
    }

    @Override
    public void onFailure(Throwable e) {
        onError(e);
    }

    public void translate() {
        local = !local;
        showQuestion(currentQuestion);
    }


    public void onNoteClicked() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.view_note_layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        Note note = new Note();
        EditText noteData = dialogView.findViewById(R.id.et_message);
        dialogBuilder.setTitle(this.getResources().getString(R.string.add_new_question_note_title));
        dialogBuilder.setPositiveButton(this.getResources().getString(R.string.add), (dialog, whichButton) -> {
            note.setNote(noteData.getText().toString());
            note.setUpdatedAt(getCurrentTime());
            note.setType("question");
            mPresenter.addNewNoteToAPI(note, questionsList.get(currentQuestion), this);
            Toast.makeText(this, "You note is saved successfully", Toast.LENGTH_SHORT).show();
        });
        dialogBuilder.setNegativeButton(this.getResources().getString(R.string.cancel), (dialog, whichButton) -> {
            dialog.dismiss();
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void onListClicked() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.fragment_exam_list, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        RecyclerView questionList = dialogView.findViewById(R.id.rv_questions_list);
        QuestionAdapter questionAdapter = new QuestionAdapter(questionsList, this, this, "exam");
        questionList.setLayoutManager(new LinearLayoutManager(this));
        questionList.setAdapter(questionAdapter);
        questionList.setHasFixedSize(true);
        dialogBuilder.setTitle("All Exam Questions");
        dialogBuilder.setNegativeButton(this.getResources().getString(R.string.cancel), (dialog, whichButton) -> {
            dialog.dismiss();
        });
        questionsListDialog = dialogBuilder.create();
        questionsListDialog.show();
    }

    public void onReportClicked() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.view_report_layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        ReportObject obj = new ReportObject();
        EditText noteData = dialogView.findViewById(R.id.et_message);
        dialogBuilder.setTitle(this.getResources().getString(R.string.report_title));
        dialogBuilder.setPositiveButton(this.getResources().getString(R.string.add), (dialog, whichButton) -> {
            obj.setMessage(noteData.getText().toString());
            obj.setQuestion_id(String.valueOf(questionsList.get(currentQuestion).getId()));
            mPresenter.reportQuestion(obj, getAccessToken());
        });
        dialogBuilder.setNegativeButton(this.getResources().getString(R.string.cancel), (dialog, whichButton) -> {
            dialog.dismiss();
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @OnClick(R.id.tv_answer_one)
    public void answerOneClicked() {
        showAnswer(1);
        questionsList.get(currentQuestion).setUserAnswer(String.valueOf(1));
    }

    @OnClick(R.id.tv_submit)
    public void submitExam() {
        showExamDialog("Submit Exam", "Would you like to submit your answers", false);
    }


    @OnClick(R.id.tv_answer_two)
    public void answerTwoClicked() {
        showAnswer(2);
        questionsList.get(currentQuestion).setUserAnswer(String.valueOf(2));
    }

    @OnClick(R.id.tv_answer_three)
    public void answerThreeClicked() {
        showAnswer(3);
        questionsList.get(currentQuestion).setUserAnswer(String.valueOf(3));
    }

    @OnClick(R.id.tv_answer_four)
    public void answerFourClicked() {
        showAnswer(4);
        questionsList.get(currentQuestion).setUserAnswer(String.valueOf(4));
    }

    private String getCurrentTime() {
        String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    private void showQuestion(int item) {
        if (local) {
            question.setText((item + 1) + " : " + questionsList.get(item).getQuestionA());
            answerOne.setText(questionsList.get(item).getAnswerA1());
            answerTwo.setText(questionsList.get(item).getAnswerA2());
            answerThree.setText(questionsList.get(item).getAnswerA3());
            answerFour.setText(questionsList.get(item).getAnswerA4());
        } else {
            question.setText((item + 1) + " : " + questionsList.get(item).getQuestionE());
            answerOne.setText(questionsList.get(item).getAnswerE1());
            answerTwo.setText(questionsList.get(item).getAnswerE2());
            answerThree.setText(questionsList.get(item).getAnswerE3());
            answerFour.setText(questionsList.get(item).getAnswerE4());
        }
        retrieveQuestionData(item);
        if (!option.isTimerType()) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            questionCounter(questionsList.get(item).getQuestionTime());
        }
        if (questionsList.get(currentQuestion).isFlag()) {
            markQuestion.setImageResource(R.drawable.blueflag);
        } else {
            markQuestion.setImageResource(R.drawable.ic_unselected_flag);
        }
    }

    public void showExamDialog(String title, String message, boolean hideNoButtom) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("submit", (dialog, id) -> {
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    AppRouter.navigateTenStatisticsScreen(getApplicationContext(), calculateExamData());
                    finish();
                });
        if (!hideNoButtom) {
            builder.setNegativeButton("No", (dialog, id) -> dialog.cancel());
        }
        AlertDialog b = builder.create();
        b.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        showExamDialog("Submit Exam", "Would you like to submit your answers", false);
    }

    @Override
    public void showQuestionByNumber(int number) {
        currentQuestion = number;
        showQuestion(number);
        if (questionsListDialog != null) {
            questionsListDialog.dismiss();
            questionsListDialog = null;
        }
    }

    private ExamData calculateExamData() {
        ExamData data = new ExamData();
        for (int i = 0; i < questionsList.size(); i++) {
            if (questionsList.get(i).isFlag()) {
                flaggedQuestions++;
            }
            if (questionsList.get(i).getUserAnswer() == null)
                unSolvedQuestions++;
            else {
                if (questionsList.get(i).getUserAnswer().equals(questionsList.get(i).getAnswer()))
                    correctAnswers++;
                else {
                    wrongAnswers++;
                }
            }
            databaseHelper.addWrongQuestion(questionsList.get(i));
        }
        data.setExamDate(getCurrentTime().toString());
        data.setCorrectAnswers(correctAnswers);
        data.setFlaggedQuestions(flaggedQuestions);
        data.setUnSolvedQuestions(unSolvedQuestions);
        data.setWrongAnswers(wrongAnswers);
        data.setQuestionNumber(questionsList.size());
        return data;
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
                viewTwo.setVisibility(View.VISIBLE);
                viewThree.setVisibility(View.GONE);
                viewFour.setVisibility(View.GONE);
                break;
            case 3:
                viewOne.setVisibility(View.GONE);
                viewTwo.setVisibility(View.GONE);
                viewThree.setVisibility(View.VISIBLE);
                viewFour.setVisibility(View.GONE);
                break;
            case 4:
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

    @Override
    public void onSuccess(QuestionListResponse response) {

        questionsList.addAll(response.getQuestions());
        currentQuestion = 0;
        showQuestion(currentQuestion);

        if (option.isTimerType()) {
            examCounter(option.getExamCalendar());
        } else {
            questionTime = option.getQuestionCalendar();
            for (int i = 0; i < response.getQuestions().size(); i++) {
                response.getQuestions().get(i).setQuestionTime(questionTime);
            }
            questionCounter(questionTime);
        }
    }

    public void loadNextQuestion() {
        if (currentQuestion < questionsList.size() - 1) {
            currentQuestion += 1;
            showQuestion(currentQuestion);
        } else {
            showExamDialog("Submit Exam", "Would you like to submit your answers", false);
        }
    }

    public void loadPreviousQuestion() {
        if (currentQuestion != 0) {
            currentQuestion -= 1;
            showQuestion(currentQuestion);
        }
    }

    public void retrieveQuestionData(int questionNumber) {
        if (questionsList.get(questionNumber).getUserAnswer() == null) {
            showAnswer(7);
        } else {
            showAnswer(Integer.parseInt(questionsList.get(questionNumber).getUserAnswer()));
        }
    }

    private void enableView(boolean status) {
        answerFour.setClickable(status);
        answerThree.setClickable(status);
        answerTwo.setClickable(status);
        answerOne.setClickable(status);
    }

    public void examCounter(long counterTime) {
        countDownTimer = new CountDownTimer(counterTime, 1000) {
            public void onTick(long millisUntilFinished) {
                //check if the exam fragment is available
                // ظظExamFragment myFragment = (ExamFragment)getFragmentManager().findFragmentByTag("EXAM_FRAGMENT");
                // if (myFragment == null || !myFragment.isVisible()) {
                if (getFragmentManager() == null) {
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                }
                long second = (millisUntilFinished / 1000) % 60;
                long minute = (millisUntilFinished / (1000 * 60)) % 60;
                long hour = (millisUntilFinished / (1000 * 60 * 60)) % 24;
                String time = String.format("%02d:%02d:%02d", hour, minute, second);
                currentQuestionTime = millisUntilFinished;
                questionCounter.setText(time);

            }

            public void onFinish() {
                showExamDialog(getResources().getString(R.string.time_up_message), "Please Submit Exam", true);
            }
        }.start();
    }

    public void questionCounter(long counterTime) {
        String msg = "time ".concat(Long.toString(counterTime));
        if (questionsList.get(currentQuestion).getQuestionTime() == 0) {
            questionCounter.setText(getResources().getString(R.string.time_up_message));
            enableView(false);
        } else {
            enableView(true);
            countDownTimer = new CountDownTimer(counterTime, 1000) {
                public void onTick(long millisUntilFinished) {
                    //check if the exam fragment is available
                    // ExamFragment myFragment = (ExamFragment)getFragmentManager().findFragmentByTag("EXAM_FRAGMENT");
                    // if (myFragment == null || !myFragment.isVisible()) {
                    if (getFragmentManager() == null) {
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                    }
                    long second = (millisUntilFinished / 1000) % 60;
                    long minute = (millisUntilFinished / (1000 * 60)) % 60;
                    long hour = (millisUntilFinished / (1000 * 60 * 60)) % 24;
                    //String time = String.format("%1$thh:%1$tM:%1$tS", millisUntilFinished);
                    String time = String.format("%02d:%02d:%02d", hour, minute, second);
                    questionsList.get(currentQuestion).setQuestionTime(millisUntilFinished);
                    Log.i("Hello", "onTick question" + millisUntilFinished);
                    questionCounter.setText(time);
                }

                public void onFinish() {
                    enableView(false);
                    questionsList.get(currentQuestion).setQuestionTime(0);
                    // Log.i("Hello", "onFinish" + currentQuestionTime);
                    questionCounter.setText(getResources().getString(R.string.time_up_message));
                }
            }.start();
        }
    }
}