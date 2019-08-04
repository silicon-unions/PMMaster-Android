package com.codesignet.pmp.basics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.codesignet.pmp.Authentication.ui.activity.LoginActivity;
import com.codesignet.pmp.app.Constants;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

public class BaseFragment<I extends BaseInteractour, V extends BaseView, P extends BasePresenter>
        extends Fragment implements BaseView {

    @Inject
    public P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachView(this);
        mPresenter.attachContext(this.getActivity());
    }

    @Override
    public void onDestroy() {
        mPresenter.deAttachView();
        mPresenter.deAttachContext();
        super.onDestroy();
    }

    @Override
    public void onError(Throwable throwable) {
        if (throwable.getMessage().equals("HTTP 401 Unauthorized"))
            restartPMP();
        else
            showMessage(throwable.getMessage());
    }

    public void restartPMP() {
        clearSharedPreferences();
        Intent i = new Intent(getActivity(), LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getActivity().startActivity(i);
        getActivity().finish();
    }

    public void clearSharedPreferences() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        preferences.edit().remove(Constants.ACCESS_TOKEN).apply();
        preferences.edit().remove(Constants.LEVEL).apply();
        preferences.edit().remove(Constants.TOKEN_TYPE).apply();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public String getAccessToken() {
        SharedPreferences prefs = this.getActivity().getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        return prefs.getString(Constants.ACCESS_TOKEN, "No name defined");//"No name defined" is the default value.
    }
}
