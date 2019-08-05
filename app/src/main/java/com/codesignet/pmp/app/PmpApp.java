package com.codesignet.pmp.app;

import android.app.Application;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PmpApp extends Application {
    long startTime;
    long stopTime;


    @Override
    public void onCreate() {
        startTime = System.currentTimeMillis();
        SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).edit();
        editor.putString(Constants.START_TIME, String.valueOf(startTime));
        editor.apply();
        super.onCreate();
    }
    private String convertLongDataToString(long time){
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }
}
