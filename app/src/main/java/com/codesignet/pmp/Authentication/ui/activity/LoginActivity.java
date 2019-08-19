package com.codesignet.pmp.Authentication.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.facebook.login.widget.LoginButton;
//import com.linkedin.platform.APIHelper;
//import com.linkedin.platform.LISessionManager;
//import com.linkedin.platform.errors.LIApiError;
//import com.linkedin.platform.errors.LIAuthError;
//import com.linkedin.platform.listeners.ApiListener;
//import com.linkedin.platform.listeners.ApiResponse;
//import com.linkedin.platform.listeners.AuthListener;
//import com.linkedin.platform.utils.Scope;


import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import me.kartikarora.potato.Potato;

public class LoginActivity extends BaseActivity<AuthenticationInteractor, AuthenticationView, AuthenticationPresenter>
        implements AuthenticationView {

    private static final String PACKAGE = "com.codesignet.pmp";
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
    @BindView(R.id.btn_login)
    Button loginBTN;
    @BindView(R.id.tv_forget_password)
    TextView forgetPasswordText;
    @BindView(R.id.pb_loading)
    ProgressBar loading;

    @BindView(R.id.linked_button)
    ImageView linked_button;
    @BindView(R.id.login_button)
    LoginButton LoginButton;

    CallbackManager callbackManager;
    FaceBookObject faceBookObject;
    LinkedInsObject linkedInsObject;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        InitializeDagger();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                                    } else {
                                        String email = me.optString("email");
                                        String id = me.optString("id");
                                        faceBookObject.setEmail(email);
                                        faceBookObject.setFacebookId(id);
                                        mPresenter.loginWithFacebook(faceBookObject);
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
       // generateHashkey();
        linked_button.setOnClickListener(v -> {
//            LISessionManager.getInstance(getApplicationContext())
//                    .init(this, buildScope(), new AuthListener() {
//                        @Override
//                        public void onAuthSuccess() {
//                            Log.i("Hello", LISessionManager
//                                    .getInstance(getApplicationContext())
//                                    .getSession().getAccessToken().toString());
//                            linkededinApiHelper();
//                        }
//
//                        @Override
//                        public void onAuthError(LIAuthError error) {
//                            Log.i("Hello", error.toString());
//                        }
//                    }, true);
        });
    }

//    public void generateHashkey() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    PACKAGE,
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.i("Hello", Base64.encodeToString(md.digest(),
//                        Base64.NO_WRAP));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.d("Hello", e.getMessage(), e);
//        } catch (NoSuchAlgorithmException e) {
//            Log.d("Hello", e.getMessage(), e);
//        }
//    }

    @Override
    public void showLoader() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        loading.setVisibility(View.GONE);
    }

//    public void linkededinApiHelper() {
//       String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,email-address)";
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

    public void showResult(JSONObject response) {
        try {
            linkedInsObject.setEmail(response.get("emailAddress").toString());
            linkedInsObject.setName(response.get("firstName").toString() + response.get("lastName").toString());
            linkedInsObject.setLinkedInId(response.get("id").toString());
            loading.setVisibility(View.VISIBLE);
            mPresenter.loginWithLinkedin(linkedInsObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
//    private static Scope buildScope() {
//        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
//        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnTextChanged(R.id.et_password)
    public void onPasswordFieldActive() {
        if (username.getText().toString().length() != 0) {
            loginBTN.setEnabled(true);
        }
    }

    @OnClick(R.id.btn_login)
    public void submitAuthentication() {
        error_text.setVisibility(View.GONE);
        if (isValid()) {
            if (Potato.potate(getApplicationContext()).Utils().isInternetConnected()) {
                loading.setVisibility(View.VISIBLE);
                mPresenter.submitLoginUser(createRequest(username.getText().toString(), password.getText().toString()));
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
            SQLiteDatabase sqLiteDatabase = toadyExamDatabaseHelper.getWritableDatabase();
            toadyExamDatabaseHelper.onUpgrade(sqLiteDatabase, 0, 0);
            sqLiteDatabase.close();


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
        Log.i("Hello",e);
        if (e.contains("HTTP 404 Not")) {
            error_text.setText("This user does not exist ");
        }
    }

    @OnClick(R.id.tv_forget_password)
    public void navigateToPassword() {
        AppRouter.navigateToForgetPasswordScreen(getApplicationContext());
    }

    @OnClick(R.id.tv_sign_up)
    public void navigateToSignUp() {
        AppRouter.navigateToSignUpScreen(getApplicationContext());
    }

    private Boolean isValid() {
        Boolean isValid;

        isValid = isEmailValid() && isPasswordValid();
        return isValid;
    }

    private Boolean isEmailValid() {
        Boolean isValid = true;
        if (username.getText() == null || username.getText().toString().length() == 0) {
            usernameLayout.setError(getString(R.string.email_field_empty_error));
            if (isValid) {
                requestFocus(usernameLayout);
            }
            isValid = false;
        } else if (!isValidEmail(username.getText().toString())) {
            usernameLayout.setError(getString(R.string.email_format_error));
            isValid = false;
        } else {
            usernameLayout.setErrorEnabled(false);
        }
        return isValid;
    }

    private Boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private Boolean isPasswordValid() {
        Boolean isValid = true;

        if (password.getText() == null || password.getText().toString().length() == 0) {
            passwordLayout.setError(getString(R.string.password_field_empty_error));
            if (isValid) {
                requestFocus(passwordLayout);
            }
            isValid = false;
        } else {
            passwordLayout.setErrorEnabled(false);
        }
        return isValid;
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

    public AuthenticationRequest createRequest(String email, String password) {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail(email);
        request.setPassword(password);
        return request;
    }
}
