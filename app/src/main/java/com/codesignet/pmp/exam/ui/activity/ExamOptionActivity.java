package com.codesignet.pmp.exam.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codesignet.pmp.BuildConfig;
import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.exam.data_access_layer.database_manager.ExamDatabaseHelper;
import com.codesignet.pmp.exam.data_access_layer.pojos.ExamOption;
import com.codesignet.pmp.exam.ui.adapter.StringAdapter;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.piotrek.customspinner.CustomSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import io.github.deweyreed.scrollhmspicker.ScrollHmsPicker;
import io.github.deweyreed.scrollhmspicker.ScrollHmsPickerDialog;
import me.kartikarora.potato.Potato;


public class ExamOptionActivity extends AppCompatActivity implements ScrollHmsPickerDialog.HmsPickHandler {

    @BindView(R.id.et_exam_question_number)
    EditText questionNumber;
    @BindView(R.id.timer_type_spinner)
    CustomSpinner timeTypeSpinner;
    @BindView(R.id.timer_type_line)
    View timerSpinnerLine;
    @BindView(R.id.error_message_sub_category)
    TextView timerSpinnerError;
    @BindView(R.id.error_message_sub_category_timer)
    TextView error_message_sub_category_timer;
    @BindView(R.id.scrollHmsPicker)
    ScrollHmsPicker scrollHmsPicker;
    @BindView(R.id.btn_start_exam)
    Button startExam;
    @BindView(R.id.bmb)
    BoomMenuButton bmb;
    @BindView(R.id.error_message_question_number)
    TextView questionNumberError;
    private Boolean timerType;
    private ArrayList<String> timerValue;
    private StringAdapter timerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_option);
        ButterKnife.bind(this);
        String[] menuItems = getResources().getStringArray(R.array.menu_items);
        String[] menuItemsColors = getResources().getStringArray(R.array.menu_items_colors);
        InitializeMenu(menuItems,menuItemsColors);
        initializeTimerType();
        if(BuildConfig.FLAVOR.equals("Free")){
            questionNumber.setEnabled(false);
            questionNumber.setText("25");
        }
        else if(BuildConfig.FLAVOR.equals("Paid")){
            questionNumber.setEnabled(true);
        }
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


    private void initializeTimerType() {
        timerValue = new ArrayList();
        timerValue.addAll(Arrays.asList(getResources().getStringArray(R.array.timer_type)));
        timerAdapter = new StringAdapter(getApplicationContext(), timerValue);
        timeTypeSpinner.setAdapter(timerAdapter);
    }


    @OnItemSelected(R.id.timer_type_spinner)
    public void onTimerSelected(int position) {
        switch (position) {
            case 0:
                timerType = true;
                //scrollHmsPicker.setVisibility(View.VISIBLE);
               // scrollHmsPicker_question.setVisibility(View.GONE);
                break;
            case 1:
                timerType = false;
               // scrollHmsPicker.setVisibility(View.GONE);
                //scrollHmsPicker_question.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick(R.id.btn_start_exam)
    public void onStartExamClicked() {
        if (!isValid())
            return;

        if (Potato.potate(getApplicationContext()).Utils().isInternetConnected()) {
            AppRouter.navigateToExamScreen(getApplicationContext(), createExamOption());
        } else {
            Toast.makeText(getApplicationContext(),getString(R.string.no_connection),Toast.LENGTH_LONG).show();
        }
    }

    private ExamOption createExamOption() {
        ExamOption examOption = new ExamOption();
        examOption.setExamCalendar(createExamTime());
        examOption.setQuestionCalendar(createQuestionTimer());
        examOption.setTimerType(timerType);
        if(BuildConfig.FLAVOR.equals("Free")){
            examOption.setQuestionNumber(Integer.parseInt("-1"));
        }
        else if(BuildConfig.FLAVOR.equals("Paid")){
            examOption.setQuestionNumber(Integer.parseInt(questionNumber.getText().toString()));
        }
        return examOption;
    }

    private Boolean isValid() {
        boolean isValid = true;
        if (questionNumber.getText().toString() == null ||
                questionNumber.getText().toString().length() == 0 ||
                Integer.parseInt(questionNumber.getText().toString()) == 0) {
            questionNumberError.setText("Please choose valid number of questions");
            questionNumberError.setVisibility(View.VISIBLE);
            isValid = false;

        }
        else if(questionNumber.getText().toString().equals(250)){
            questionNumberError.setText("Max number of Question is 250");
            questionNumberError.setVisibility(View.VISIBLE);
        }
        else {
            questionNumberError.setVisibility(View.GONE);
        }

       /* if (timeTypeSpinner.getSelectedItemPosition() == 0) {
            isValid = false;
            timerSpinnerError.setVisibility(View.VISIBLE);
            timerSpinnerLine.setBackgroundColor(getResources().getColor(R.color.orange_pink));
        } else {
            timerSpinnerError.setVisibility(View.GONE);
            timerSpinnerLine.setBackgroundColor(getResources().getColor(R.color.dark));
        }*/

            if (scrollHmsPicker.getHours() == 0 && scrollHmsPicker.getMinutes() == 0 && scrollHmsPicker.getSeconds() == 0) {
                error_message_sub_category_timer.setVisibility(View.VISIBLE);
                isValid = false;
            } else{
                error_message_sub_category_timer.setVisibility(View.GONE);
            }
        return isValid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private long createExamTime() {
        //Calendar examCalendar = Calendar.getInstance();
        //examCalendar.set(Calendar.HOUR, scrollHmsPicker.getHours() - 10);
        //examCalendar.set(Calendar.MINUTE, scrollHmsPicker.getMinutes());
        //examCalendar.set(Calendar.SECOND, scrollHmsPicker.getSeconds());
        //return examCalendar.getTimeInMillis();
        Long millis = scrollHmsPicker.getHours()*3600000l;
        millis+=scrollHmsPicker.getMinutes()*60000l;
        millis+=scrollHmsPicker.getSeconds()*1000;
        return millis;
    }

    private long createQuestionTimer() {
        /*Calendar questionCalendar = Calendar.getInstance();
        questionCalendar.set(Calendar.HOUR, scrollHmsPicker_question.getHours() - 10);
        questionCalendar.set(Calendar.MINUTE, scrollHmsPicker_question.getMinutes());
        questionCalendar.set(Calendar.SECOND, scrollHmsPicker_question.getSeconds());
        return questionCalendar.getTimeInMillis();*/

        Long millis = scrollHmsPicker.getHours()*3600000l;
        millis+=scrollHmsPicker.getMinutes()*60000l;
        millis+=scrollHmsPicker.getSeconds()*1000;
        return millis;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onHmsPick(int i, int i1, int i2, int i3) {
    }

}