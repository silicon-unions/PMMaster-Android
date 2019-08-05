package com.codesignet.pmp.Authentication.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codesignet.pmp.Authentication.data_access_layer.AuthenticationInteractor;
import com.codesignet.pmp.Authentication.data_access_layer.modle.AuthenticationResponse;
import com.codesignet.pmp.Authentication.dependencies.AuthenticationModule;
import com.codesignet.pmp.Authentication.dependencies.DaggerAuthenticationComponent;
import com.codesignet.pmp.Authentication.presenter.AuthenticationPresenter;
import com.codesignet.pmp.Authentication.view.AuthenticationView;
import com.codesignet.pmp.R;
import com.codesignet.pmp.basics.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import me.kartikarora.potato.Potato;

public class ForgerPasswordActivity extends BaseActivity<AuthenticationInteractor, AuthenticationView, AuthenticationPresenter>
        implements AuthenticationView {

    @BindView(R.id.btn_back)
    ImageView back;
    @BindView(R.id.til_email)
    TextInputLayout email_Layout;
    @BindView(R.id.et_email)
    EditText email;
    @BindView(R.id.btn_send)
    Button send;
    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.tv_error)
    TextView error_text;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        InitializeDagger();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forger_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_back)
    public void goBack() {
        finish();
    }

    @OnTextChanged(R.id.et_email)
    public void emailTextChanged() {
        if (email.getText().toString().length() != 0) {
            send.setEnabled(true);
        }
    }

    @OnClick(R.id.btn_send)
    public void sendForgetPasswordRequest() {
        if (isEmailValid()) {
            if (Potato.potate(getApplicationContext()).Utils().isInternetConnected()) {
                loading.setVisibility(View.VISIBLE);
                mPresenter.getPassword(email.getText().toString());
            }
        }
    }

    @Override
    public void onSuccess(AuthenticationResponse response) {
    }

    @Override
    public void showLoader() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        loading.setVisibility(View.GONE);
    }
    @Override
    public void onFailure(String e) {
        loading.setVisibility(View.GONE);
        error_text.setVisibility(View.VISIBLE);
        error_text.setText(e);
    }

    private Boolean isEmailValid() {
        Boolean isValid = true;
        if (email.getText() == null || email.getText().toString().length() == 0) {
            email_Layout.setError(getString(R.string.email_field_empty_error));
            if (isValid) {
                requestFocus(email_Layout);
            }
            isValid = false;
        } else if (!isValidEmail(email.getText().toString())) {
            email_Layout.setError(getString(R.string.email_format_error));
            isValid = false;
        } else {
            email_Layout.setErrorEnabled(false);
        }
        return isValid;
    }

    private Boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void InitializeDagger() {
        DaggerAuthenticationComponent
                .builder()
                .authenticationModule(new AuthenticationModule(this))
                .build()
                .inject(this);
    }
}
