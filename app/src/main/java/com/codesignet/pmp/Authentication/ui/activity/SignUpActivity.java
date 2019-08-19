package com.codesignet.pmp.Authentication.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codesignet.pmp.Authentication.data_access_layer.AuthenticationInteractor;
import com.codesignet.pmp.Authentication.data_access_layer.modle.AuthenticationRequest;
import com.codesignet.pmp.Authentication.data_access_layer.modle.AuthenticationResponse;
import com.codesignet.pmp.Authentication.data_access_layer.modle.FaceBookObject;
import com.codesignet.pmp.Authentication.data_access_layer.modle.LinkedInsObject;
import com.codesignet.pmp.Authentication.dependencies.AuthenticationModule;
import com.codesignet.pmp.Authentication.dependencies.DaggerAuthenticationComponent;
import com.codesignet.pmp.Authentication.presenter.AuthenticationPresenter;
import com.codesignet.pmp.Authentication.view.AuthenticationView;
import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.app.Constants;
import com.codesignet.pmp.basics.BaseActivity;
import com.codesignet.pmp.exam.data_access_layer.database_manager.ExamDatabaseHelper;
import com.codesignet.pmp.home.data_access_layer.ToadyExamDatabaseHelper;
import com.codesignet.pmp.notes.data_access_layer.database.NotesDatabaseHelper;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
//import com.linkedin.platform.APIHelper;
//import com.linkedin.platform.LISessionManager;
//import com.linkedin.platform.errors.LIApiError;
//import com.linkedin.platform.errors.LIAuthError;
//import com.linkedin.platform.listeners.ApiListener;
//import com.linkedin.platform.listeners.ApiResponse;
//import com.linkedin.platform.listeners.AuthListener;
//import com.linkedin.platform.utils.Scope;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import me.kartikarora.potato.Potato;

public class SignUpActivity extends BaseActivity<AuthenticationInteractor, AuthenticationView, AuthenticationPresenter>
        implements AuthenticationView {


    @BindView(R.id.btn_back)
    ImageView back;
    @BindView(R.id.tv_error)
    TextView error_text;
    @BindView(R.id.til_username)
    TextInputLayout usernameLayout;
    @BindView(R.id.et_username)
    EditText username;
    @BindView(R.id.til_password)
    TextInputLayout passwordLayout;
    @BindView(R.id.et_password)
    EditText password;
    @BindView(R.id.til_confirm_password)
    TextInputLayout confirmPasswordLayout;
    @BindView(R.id.et_confirm_password)
    EditText confirmPassword;
    @BindView(R.id.til_email)
    TextInputLayout email_Layout;
    @BindView(R.id.et_email)
    EditText email;
    @BindView(R.id.btn_send)
    Button send;
    @BindView(R.id.pb_loading)
    ProgressBar loading;
    @BindView(R.id.linked_button)
    ImageView linked_button;
    @BindView(R.id.login_button)
    com.facebook.login.widget.LoginButton LoginButton;

    CallbackManager callbackManager;
    FaceBookObject faceBookObject;
    LinkedInsObject linkedInsObject;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        InitializeDagger();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        faceBookObject = new FaceBookObject();
        linkedInsObject = new LinkedInsObject();

        callbackManager = CallbackManager.Factory.create();
        LoginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        LoginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), (me, response) -> {
                                    if (response.getError() != null) {
                                        // handle error
                                    } else {
                                        String email = me.optString("email");
                                        String id = me.optString("id");
                                        String name = me.optString("name");
                                        faceBookObject.setEmail(email);
                                        faceBookObject.setFacebookId(id);
                                        faceBookObject.setName(name);
                                        mPresenter.signUpWithFacebook(faceBookObject);
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

//        linked_button.setOnClickListener(v->{
//            LISessionManager.getInstance(getApplicationContext())
//                    .init(this, buildScope(), new AuthListener() {
//                        @Override
//                        public void onAuthSuccess() {
//                            Log.i("Hello",LISessionManager
//                                    .getInstance(getApplicationContext())
//                                    .getSession().getAccessToken().toString());
//                            linkededinApiHelper();
//                        }
//
//                        @Override
//                        public void onAuthError(LIAuthError error) {
//                            Log.i("Hello",error.toString());
//                        }
//                    }, true);
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
//        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showLoader() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        loading.setVisibility(View.GONE);
    }

//    public void linkededinApiHelper(){
//        String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,email-address)";
//
//        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
//        apiHelper.getRequest(this, url, new ApiListener() {
//            @Override
//            public void onApiSuccess(ApiResponse apiResponse) {
//                // Success!\
//                showResult(apiResponse.getResponseDataAsJson());
//            }
//
//            @Override
//            public void onApiError(LIApiError liApiError) {
//                // Error making GET request!
//            }
//        });
//    }
    public  void  showResult(JSONObject response){
        try {
            linkedInsObject.setEmail(response.get("emailAddress").toString());
            linkedInsObject.setName(response.get("firstName").toString()+response.get("lastName").toString());
            linkedInsObject.setLinkedInId(response.get("id").toString());
            loading.setVisibility(View.VISIBLE);
            mPresenter.signUpWithLinkedIn(linkedInsObject);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

//    private static Scope buildScope() {
//        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
//    }

    @OnClick(R.id.btn_back)
    public void goBack() {
        AppRouter.navigateToChoose(getApplicationContext());
        finish();
    }

    @OnClick(R.id.btn_send)
    public void sendNewUser() {
        error_text.setVisibility(View.GONE);
        if (isValid()) {
            if (Potato.potate(getApplicationContext()).Utils().isInternetConnected()) {
                loading.setVisibility(View.VISIBLE);
                mPresenter.submitNewUser(createNewUser(username.getText().toString(), email.getText().toString(), password.getText().toString()));
            } else {
                error_text.setVisibility(View.VISIBLE);
                error_text.setText(getString(R.string.no_connection));
            }
        }
    }

    @Override
    public void onSuccess(AuthenticationResponse response) {
        loading.setVisibility(View.GONE);
        if (response.isSuccess()) {
            SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).edit();
            editor.putString(Constants.ACCESS_TOKEN, response.getUser().getApiToken());
            editor.putString(Constants.LEVEL, response.getUser().getLevel());
            editor.putString(Constants.TOKEN_TYPE, response.getTokenType());
            editor.putString(Constants.USER_NAME, response.getUser().getName());
            editor.putString(Constants.USER_EMAIL, response.getUser().getEmail());
            editor.putBoolean(Constants.ALL_QUESTIONS_DOWNLOADED, false);
            editor.apply();

            NotesDatabaseHelper databaseHelper = new NotesDatabaseHelper(getApplicationContext());
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            databaseHelper.onUpgrade(db, 0, 0);
            db.close();

            ExamDatabaseHelper examDatabaseHelper = new ExamDatabaseHelper(getApplicationContext());
            SQLiteDatabase examdb = examDatabaseHelper.getWritableDatabase();
            examDatabaseHelper.onUpgrade(examdb, 0, 0);
            examdb.close();

            ToadyExamDatabaseHelper toadyExamDatabaseHelper = new ToadyExamDatabaseHelper(getApplicationContext());
            SQLiteDatabase toadyExamsqLiteDatabase = toadyExamDatabaseHelper.getWritableDatabase();
            toadyExamDatabaseHelper.onUpgrade(toadyExamsqLiteDatabase, 0, 0);
            toadyExamsqLiteDatabase.close();

            AppRouter.navigateToPracticesDownlaod(getApplicationContext());
            finish();
        } else {
            error_text.setVisibility(View.VISIBLE);
            error_text.setText(response.getReason());
        }
    }

    @Override
    public void onFailure(String e) {
        loading.setVisibility(View.GONE);
        error_text.setVisibility(View.VISIBLE);
        if (e.contains("HTTP 400")){
            error_text.setText("This user already exists");
        }
    }

    @OnTextChanged(R.id.et_email)
    public void emailTextChanged() {
        if (email.getText().toString().length() != 0 && confirmPassword.getText().toString().length() != 0
                && password.getText().toString().length() != 0 && username.getText().toString().length() != 0) {
            send.setEnabled(true);
        }
    }


    private Boolean isValid() {
        Boolean isValid = true;
        if (email.getText() == null || email.getText().toString().length() == 0) {
            email_Layout.setError(getString(R.string.email_field_empty_error));
            if (isValid) {
                requestFocus(email_Layout);
            }
            isValid = false;
        } else if (!isValidEmail(email.getText().toString())) {
            email_Layout.setError(getString(R.string.email_format_error));
            if (isValid) {
                requestFocus(email_Layout);
            }
            isValid = false;
        } else {
            email_Layout.setErrorEnabled(false);
        }
        if (username.getText() == null || username.getText().toString().length() == 0) {
            usernameLayout.setError(getString(R.string.full_name_error_message));
            if (isValid) {
                requestFocus(usernameLayout);
            }
            isValid = false;
        } else {
            usernameLayout.setErrorEnabled(false);
        }
        if (password.getText() == null || password.getText().toString().length() == 0) {
            passwordLayout.setError(getString(R.string.password_field_empty_error));
            if (isValid) {
                requestFocus(passwordLayout);
            }
            isValid = false;
        } else {
            passwordLayout.setErrorEnabled(false);
        }
        if (confirmPassword.getText() == null || confirmPassword.getText().toString().length() == 0) {
            confirmPasswordLayout.setError(getString(R.string.password_field_empty_error));
            if (isValid) {
                requestFocus(confirmPasswordLayout);
            }
            isValid = false;
        } else if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
            confirmPasswordLayout.setError(getString(R.string.password_confirm_message));
            if (isValid) {
                requestFocus(confirmPasswordLayout);
            }
            isValid = false;
        } else {
            confirmPasswordLayout.setErrorEnabled(false);
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

    private AuthenticationRequest createNewUser(String name, String email, String password) {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setName(name);
        request.setEmail(email);
        request.setPassword(password);
        return request;
    }
}
