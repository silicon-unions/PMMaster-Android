package com.codesignet.pmp.basics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.codesignet.pmp.Authentication.ui.activity.LoginActivity;
import com.codesignet.pmp.app.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.inject.Inject;

public class BaseActivity<I extends BaseInteractour, V extends BaseView, P extends BasePresenter>
        extends AppCompatActivity implements BaseView {

    @Inject
    public P mPresenter;
    long stopTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachContext(this);
        mPresenter.attachView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        mPresenter.deAttachView();
        mPresenter.deAttachContext();
        stopTime = System.currentTimeMillis();
        SharedPreferences prefs = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        calculateAppTime(prefs.getString(Constants.START_TIME, ""), String.valueOf(stopTime));
        super.onDestroy();
    }

    @Override
    public void onError(Throwable throwable) {
        if (throwable.getMessage().equals("HTTP 401 Unauthorized"))
            restartPMP();
        else
            showMessage(throwable.getMessage());
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public String getAccessToken() {
        SharedPreferences prefs = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        return prefs.getString(Constants.ACCESS_TOKEN, "No name defined");//"No name defined" is the default value.
    }

    public String getPreferredTiem() {
        SharedPreferences prefs = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        return prefs.getString(Constants.PRACTICE_TYPE, "00:00");//"No name defined" is the default value.
    }

    public void clearSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        preferences.edit().remove(Constants.ACCESS_TOKEN).apply();
        preferences.edit().remove(Constants.LEVEL).apply();
        preferences.edit().remove(Constants.TOKEN_TYPE).apply();
    }

    public void restartPMP() {
        clearSharedPreferences();
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    public void loadAndAddToBackStackFragment(int container, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(container, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    private void calculateAppTime(String dateStart, String dateStop) {
        try {
            long diff = Long.valueOf(dateStop) -  Long.valueOf(dateStart);
//            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
//            long diffHours = diff / (60 * 60 * 1000) % 24;
//            long diffDays = diff / (24 * 60 * 60 * 1000);
            System.out.print(diffMinutes + " minutes, ");

            SaveTime((int) diffMinutes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SaveTime(int time) {
        SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).edit();
        editor.putInt(Constants.APP_TIME, time);
        editor.apply();

//        SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).edit();
//        SharedPreferences prefs = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
//        if (prefs.getInt(Constants.APP_TIME, 0)!=0){
//            editor.putInt(Constants.APP_TIME, (time + prefs.getInt(Constants.APP_TIME, 0)));
//            editor.apply();
//        }
    }

}
