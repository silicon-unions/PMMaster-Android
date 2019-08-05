package com.codesignet.pmp.ask_instructor.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.ask_instructor.adapters.AskAdapter;
import com.codesignet.pmp.ask_instructor.data_access_layer.AskInteractor;
import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.AskInstructorQuestionItem;
import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.QuestionObject;
import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.QuestionObjectID;
import com.codesignet.pmp.ask_instructor.dependencies.AskModule;
import com.codesignet.pmp.ask_instructor.dependencies.DaggerAskComponent;
import com.codesignet.pmp.ask_instructor.presenter.AskPresenter;
import com.codesignet.pmp.ask_instructor.view.AskView;
import com.codesignet.pmp.ask_instructor.view.OnActionCallBack;
import com.codesignet.pmp.basics.BaseActivity;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.kartikarora.potato.Potato;

public class AskScreenActivity extends BaseActivity<AskInteractor, AskView, AskPresenter>
        implements AskView, OnActionCallBack {

    @BindView(R.id.rc_ask_question)
    RecyclerView rc_question;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.bmb)
    BoomMenuButton bmb;

    @BindView(R.id.tv_message)
    TextView tv_message;

    private AskAdapter askAdapter;
    private ArrayList<AskInstructorQuestionItem> questionsList;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        InitializeDagger();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_instructor);
        ButterKnife.bind(this);
        questionsList = new ArrayList();
        askAdapter = new AskAdapter(this, questionsList);
        String[] menuItems = getResources().getStringArray(R.array.menu_items);
        String[] menuItemsColors = getResources().getStringArray(R.array.menu_items_colors);
        InitializeMenu(menuItems,menuItemsColors);
        InitializeRecyclerView();
        if (Potato.potate(getApplicationContext()).Utils().isInternetConnected()) {
            mPresenter.getAskList(getAccessToken());
        }
        else{
            showConnectionError("Connection error","This feature requires an active internet connection, " +
                    "Please check your connection and try again.");
        }
        refreshLayout.setOnRefreshListener(() -> mPresenter.getAskList(getAccessToken()));
}

    public void showConnectionError(String title, String message) {
        AlertDialog alert;

        AlertDialog.Builder builder = new AlertDialog.Builder(AskScreenActivity.this);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("ok", (dialog, id) -> {
                    AppRouter.navigateToHomeScreen(getApplicationContext());
                    finish();
                });
        alert = builder.create();
        alert.show();
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
                            AppRouter.navigateToExamOptionScreen(getApplicationContext());
                            finish();
                            break;
                        case 3:
                            AppRouter.navigateToNoteScreen(getApplicationContext());
                            finish();
                            break;
                        case 4:
                            this.onBoomDidHide();
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

    private void InitializeDagger() {
        DaggerAskComponent
                .builder()
                .askModule(new AskModule(this))
                .build()
                .inject(this);
    }

    private void InitializeRecyclerView() {
        rc_question.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_question.setLayoutManager(mLayoutManager);
        rc_question.setAdapter(askAdapter);
    }

    @Override
    public void showLoader() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoader() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showAllQuestion(ArrayList<AskInstructorQuestionItem> askQuestionsList) {
        if (askQuestionsList.size()==0){
            tv_message.setVisibility(View.VISIBLE);
        }
        else {
            tv_message.setVisibility(View.GONE);
        }
        questionsList.clear();
        questionsList.addAll(askQuestionsList);
        askAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAskedQuestionDialog(AskInstructorQuestionItem askInstructorQuestionItem) {
        mPresenter.updateQuestionStatus(getAccessToken(), new QuestionObjectID(askInstructorQuestionItem.getId()));
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.view_question_layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        TextView tv_question = dialogView.findViewById(R.id.tv_question);
        TextView tv_answer = dialogView.findViewById(R.id.tv_answer);
        TextView tv_status = dialogView.findViewById(R.id.tv_status);
        TextView answered_time = dialogView.findViewById(R.id.answered_time);
        ImageView imageView_answer = dialogView.findViewById(R.id.imageView_answer);

        tv_question.setText(askInstructorQuestionItem.getQuestion());

        if (askInstructorQuestionItem.getAnswer() != null) {
            tv_answer.setText(askInstructorQuestionItem.getAnswer());
            answered_time.setText(askInstructorQuestionItem.getUpdatedAt());
        } else {
            imageView_answer.setVisibility(View.GONE);
            tv_answer.setVisibility(View.GONE);
            tv_status.setVisibility(View.VISIBLE);
        }
        dialogBuilder.setTitle(getApplicationContext().getResources().getString(R.string.question));
        dialogBuilder.setPositiveButton(getApplicationContext().getResources().getString(R.string.ask_new_question), (dialog, whichButton) -> {
            showAskNewQuestionDialog();
        });
        dialogBuilder.setNegativeButton(getApplicationContext().getResources().getString(R.string.done), (dialog, whichButton) -> {
            if (Potato.potate(getApplicationContext()).Utils().isInternetConnected()) {
                mPresenter.getAskList(getAccessToken());
            }
            else{
                showConnectionError("Connection error","This feature requires an active internet connection, " +
                        "Please check your connection and try again.");
            }
            dialog.dismiss();
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    @Override
    public void onDeleteQuestion(int id, int position) {
        QuestionObjectID questionObjectID = new QuestionObjectID(id);
        showDeleteDialog(questionObjectID, position, getResources().getString(R.string.delete_confirmation_title),
                getResources().getString(R.string.ask_delete_message));
    }

    @Override
    public void onQuestionAdded(String reason) {
        Toast.makeText(getApplicationContext(), reason, Toast.LENGTH_LONG).show();
        mPresenter.getAskList(getAccessToken());
    }

    @OnClick(R.id.btn_ask_question)
    public void askNewQuestion() {
        showAskNewQuestionDialog();
    }

    public void onAddQuestion(QuestionObject questionItem) {
        if (Potato.potate(getApplicationContext()).Utils().isInternetConnected()) {
            mPresenter.addNewQuestion(getAccessToken(), questionItem);
        }
        else{
            showConnectionError("Connection error","This feature requires an active internet connection, " +
                    "Please check your connection and try again.");
        }
    }

    @Override
    public void showAskNewQuestionDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.ask_question_layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        final EditText questionEditText = dialogView.findViewById(R.id.et_message);
        dialogBuilder.setTitle(getApplicationContext().getResources().getString(R.string.ask_question));
        dialogBuilder.setPositiveButton(getApplicationContext().getResources().getString(R.string.ask), (dialog, whichButton) -> {
            getNewQuestionData(questionEditText.getText().toString());
        });
        dialogBuilder.setNegativeButton(getApplicationContext().getResources().getString(R.string.cancel), (dialog, whichButton) -> {
            dialog.dismiss();
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void showDeleteDialog(QuestionObjectID questionObjectID, int position, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    if (Potato.potate(getApplicationContext()).Utils().isInternetConnected()) {
                        mPresenter.deleteQuestion(getAccessToken(), questionObjectID);
                    }
                    else{
                        showConnectionError("Connection error","This feature requires an active internet connection, " +
                                "Please check your connection and try again.");
                    }
                    questionsList.remove(questionsList);
                    askAdapter.notifyDataSetChanged();
                    askAdapter.notifyItemRemoved(position);
                })
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void getNewQuestionData(String question) {
        if (question == null || question.equals("") || question.equals("null") || question.isEmpty())
            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.ask_question_message), Toast.LENGTH_SHORT).show();
        else
            onAddQuestion(new QuestionObject(question));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppRouter.navigateToHomeScreen(getApplicationContext());
    }
}
