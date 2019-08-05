package com.codesignet.pmp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.app.Constants;

public class SplashActivity extends AppCompatActivity {

    private String accessToken;
    private boolean all_question_Download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_splash);
        Handler timer = new Handler();
        timer.postDelayed(() -> {
            SharedPreferences prefs = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
            accessToken = prefs.getString(Constants.ACCESS_TOKEN, "");
            all_question_Download = prefs.getBoolean(Constants.ALL_QUESTIONS_DOWNLOADED, false);
            if (accessToken.equals("") || accessToken == null || accessToken.equals("null") || accessToken.isEmpty()) {
                AppRouter.navigateToChoose(getApplicationContext());
                finish();
            } else {
                if (!all_question_Download) {
                    AppRouter.navigateToPracticesDownlaod(getApplicationContext());
                    finish();
                } else {
                    AppRouter.navigateToHomeScreen(getApplicationContext());
                    finish();
                }
            }
        }, 1100);
    }
}
