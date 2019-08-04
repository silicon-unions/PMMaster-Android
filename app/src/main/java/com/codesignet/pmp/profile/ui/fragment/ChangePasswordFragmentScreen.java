package com.codesignet.pmp.profile.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.codesignet.pmp.R;
import com.codesignet.pmp.profile.data_access_layer.pojos.ProfileUpdateObject;
import com.codesignet.pmp.profile.view.OnUpdateCallBack;
import com.codesignet.pmp.profile.view.ProfileNavigateCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordFragmentScreen extends Fragment {

    @BindView(R.id.til_password)
    TextInputLayout passwordLayout;
    @BindView(R.id.et_password)
    EditText password;
    @BindView(R.id.til_confirm_password)
    TextInputLayout confirmPasswordLayout;
    @BindView(R.id.et_confirm_password)
    EditText confirmPassword;

    private OnUpdateCallBack listeners;
    private ProfileNavigateCallBack navigateCallBack;

    public static ChangePasswordFragmentScreen newInstance(OnUpdateCallBack listeners, ProfileNavigateCallBack navigateCallBack) {
        ChangePasswordFragmentScreen fragment = new ChangePasswordFragmentScreen();
        fragment.listeners = listeners;
        fragment.navigateCallBack = navigateCallBack;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_password_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private boolean isValid() {
        boolean isValid = true;
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

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @OnClick(R.id.btn_user)
    public void onUpdateClicked() {
        if (!isValid())
            return;
        ProfileUpdateObject pojo = new ProfileUpdateObject();
        pojo.setPassword(password.getText().toString());
        listeners.onUpdateClicked(pojo);
    }
}
