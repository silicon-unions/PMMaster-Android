package com.codesignet.pmp.Authentication.dependencies;

import com.codesignet.pmp.Authentication.ui.activity.ForgerPasswordActivity;
import com.codesignet.pmp.Authentication.ui.activity.LoginActivity;
import com.codesignet.pmp.Authentication.ui.activity.SignUpActivity;

import dagger.Component;

@Component(modules = AuthenticationModule.class)
public interface AuthenticationComponent {
    void inject(LoginActivity loginFragment);

    void inject(ForgerPasswordActivity forgerPasswordFragment);

    void inject(SignUpActivity signUpFragment);

}
