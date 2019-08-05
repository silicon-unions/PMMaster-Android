package com.codesignet.pmp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codesignet.pmp.app.AppRouter;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void goToLogin() {
        AppRouter.navigateToLoginScreen(getApplicationContext());
    }

    @OnClick(R.id.btn_sign_up)
    public void goTSignUp() {
        AppRouter.navigateToSignUpScreen(getApplicationContext());
        finish();
    }
}
