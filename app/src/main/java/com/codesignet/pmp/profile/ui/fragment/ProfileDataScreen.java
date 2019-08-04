package com.codesignet.pmp.profile.ui.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.allyants.notifyme.NotifyMe;
import com.bumptech.glide.Glide;
import com.codesignet.pmp.Authentication.ui.activity.LoginActivity;
import com.codesignet.pmp.R;
import com.codesignet.pmp.app.Constants;
import com.codesignet.pmp.home.ui.activity.HomeActivity;
import com.codesignet.pmp.profile.data_access_layer.pojos.User;
import com.codesignet.pmp.profile.view.ProfileNavigateCallBack;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.kartikarora.potato.Potato;

import static android.content.Context.MODE_PRIVATE;

public class ProfileDataScreen extends Fragment
        implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.iv_profile)
    ImageView profileImage;

    @BindView(R.id.iv_big_profile)
    ImageView iv_big_profile;

    @BindView(R.id.tv_name)
    TextView nameInfo;

    @BindView(R.id.tv_email)
    TextView emailInfo;

    @BindView(R.id.tv_exam_time_value)
    TextView tv_exam_time_value;

    @BindView(R.id.tv_preferred_time_value)
    TextView tv_preferred_time_value;

    @BindView(R.id.container)
    FrameLayout container;

    private Calendar mCalendar;
    private User user;
    private ProfileNavigateCallBack callBack;
    private long examDate;
    private String preferredTime;
    private Bitmap profileBitmap;
    private static final int PROFILE_PIC_UPDATED = 200;
    private SharedPreferences.Editor editor;

    private Calendar now;

    public static Fragment newInstance(User user, ProfileNavigateCallBack callBack,
                                       long examDate, String preferredTime, Bitmap bitmap) {
        ProfileDataScreen fragment = new ProfileDataScreen();
        fragment.user = user;
        fragment.callBack = callBack;
        fragment.examDate = examDate;
        fragment.preferredTime = preferredTime;
        fragment.profileBitmap = bitmap;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_data, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateScreenData();
        editor = getActivity().getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).edit();
        now = Calendar.getInstance();
        emailInfo.setText(user.getEmail());
        nameInfo.setText(user.getName());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (profileBitmap != null) {
            profileBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Glide.with(this)
                    .load(stream.toByteArray())
                    .asBitmap()
                    .into(profileImage);
            Glide.with(this)
                    .load(stream.toByteArray())
                    .asBitmap()
                    .into(iv_big_profile);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        updateScreenData();
        emailInfo.setText(user.getEmail());
        nameInfo.setText(user.getName());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (profileBitmap != null) {
            profileBitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            Glide.with(this)
                    .load(stream.toByteArray())
                    .asBitmap()
                    .into(profileImage);
            Glide.with(this)
                    .load(stream.toByteArray())
                    .asBitmap()
                    .into(iv_big_profile);
        }
    }

    private void updateScreenData() {
        mCalendar = Calendar.getInstance();
        String dateString;
        if (examDate == 0) {
            dateString = "";
        } else {
            dateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(examDate));
        }

        tv_exam_time_value.setText(dateString);
        tv_preferred_time_value.setText(preferredTime);
    }

    @OnClick(R.id.ll_edit)
    public void onEditButtonClicked() {
        if (Potato.potate(getActivity()).Utils().isInternetConnected()) {
            callBack.gotoEditProfileScreen();
        } else {
            showConnectionError("Connection error", "This feature requires an active internet connection, " +
                    "Please check your connection and try again.");
        }
    }

    public void showConnectionError(String title, String message) {
        AlertDialog alert;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("ok", (dialog, id) -> {
                    dialog.dismiss();
                });
        alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.ll_exams_settings)
    public void onExamsSettingsClicked() {
        DatePickerDialog date = new DatePickerDialog(getActivity(), createDatePicker(), mCalendar
                .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH));
        date.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        date.show();
    }

    private DatePickerDialog.OnDateSetListener createDatePicker() {
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
            setExamDate(simpleDateFormat.format(mCalendar.getTime()));
            tv_exam_time_value.setText(simpleDateFormat.format(mCalendar.getTime()));
            Date d;
            try {
                d = simpleDateFormat.parse(simpleDateFormat.format(mCalendar.getTime()));
                long milliseconds = d.getTime();
                editor.putLong(Constants.Exam_TIME, milliseconds);
                editor.commit();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        };

        return date;
    }

    @OnClick(R.id.ll_Preferred_stud_settings)
    public void onStudSettingsClicked() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(),
                (timePicker, selectedHour, selectedMinute) -> {
                    String timetype = "";
                    if (mcurrentTime.get(Calendar.AM_PM) == Calendar.AM)
                        timetype = "AM";
                    else if (mcurrentTime.get(Calendar.AM_PM) == Calendar.PM)
                        timetype = "PM";
                    tv_preferred_time_value.setText(selectedHour + ":" + selectedMinute + " " + timetype);
                    now.set(Calendar.HOUR_OF_DAY, selectedHour);
                    now.set(Calendar.MINUTE, selectedMinute);
                    setPreferredTime();
                }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.setCancelable(false);
        mTimePicker.show();
    }

    @OnClick(R.id.ll_logout)
    public void onLogoutClicked() {
        restartPMP();
    }


    @OnClick(R.id.ll_about)
    public void onAboutClicked() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.fragment_about_us, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        dialogBuilder.setTitle("ABOUT US");
        dialogBuilder.setPositiveButton("OK", (dialog, whichButton) -> {
            dialog.dismiss();
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String myFormat = "MMM dd, yyyy"; //In which you need put here
        SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.US);
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setExamDate(sdformat.format(mCalendar.getTime()));
    }

    public void restartPMP() {
        clearSharedPreferences();
        Intent i = new Intent(getActivity(), LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getActivity().startActivity(i);
        getActivity().getApplicationContext().getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).edit().clear().commit();
        getActivity().finish();
    }

    public void clearSharedPreferences() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        preferences.edit().remove(Constants.ACCESS_TOKEN).apply();
        preferences.edit().remove(Constants.LEVEL).apply();
        preferences.edit().remove(Constants.TOKEN_TYPE).apply();
    }

    public void setExamDate(String examDate) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).edit();
        editor.putString(Constants.Exam_TIME, examDate);
        editor.apply();
    }

    public void setPreferredTime() {
        String timetype = "";

        NotifyMe.Builder notifyMe = new NotifyMe.Builder(getActivity());
        notifyMe.title("PMP master");
        notifyMe.content("It is the study time");
        notifyMe.time(now);//The time to popup notification
        notifyMe.delay(1000);//Delay in ms
        notifyMe.large_icon(R.drawable.ic_pmp_logo);
        notifyMe.addAction(new Intent(getActivity(), HomeActivity.class), "let's practice"); //The action will call the intent when pressed
        notifyMe.build();

        now.add(Calendar.MINUTE, 2);
        if (now.get(Calendar.AM_PM) == Calendar.AM)
            timetype = "AM";
        else if (now.get(Calendar.AM_PM) == Calendar.PM)
            timetype = "PM";
        String newDate = now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + " " + timetype;
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).edit();
        editor.putString(Constants.PRACTICE_TYPE, newDate);
        editor.apply();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROFILE_PIC_UPDATED) {
            int addID = data.getIntExtra("addressID", 0);
        }
    }

}
