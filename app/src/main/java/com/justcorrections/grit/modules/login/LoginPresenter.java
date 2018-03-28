package com.justcorrections.grit.modules.login;

import com.justcorrections.grit.auth.GritAuthentication;
import com.justcorrections.grit.auth.GritAuthentication.GritLoginListener;
import com.justcorrections.grit.auth.GritUser;

/**
 * Created by ianwillis on 3/9/18.
 */

public class LoginPresenter {

    private LoginFragment view;
    private GritAuthentication auth;

    public LoginPresenter(GritAuthentication auth) {
        this.auth = auth;
    }

    public void attachTo(LoginFragment view) {
        this.view = view;
    }

    public void detachFromView() {
        view = null;
    }

    public void onLoginButtonPressed(String email, String password) {
        System.out.println("Ian working on it " + auth.getCurrentUser());
        auth.login(email, password, new GritLoginListener() {
            @Override
            public void onSuccess(GritUser user) {
                // switch fragment
                System.out.println("Ian it worked " + auth.getCurrentUser());
            }

            @Override
            public void onFailure(String errorMessage) {
                view.setEmailInputError(errorMessage);
            }
        });
    }

    public void onCreateAccountButtonPressed() {

    }

    public void onForgotPasswordButtonPressed() {

    }
}
