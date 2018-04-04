package com.justcorrections.grit.modules.signin;

import com.justcorrections.grit.auth.GritAuthentication;
import com.justcorrections.grit.auth.GritAuthentication.GritAuthCallback;

/**
 * Created by ianwillis on 3/9/18.
 */

public class SigninPresenter {

    private SigninFragment view;
    private GritAuthentication auth;

    public SigninPresenter(GritAuthentication auth) {
        this.auth = auth;
    }

    public void attachTo(SigninFragment view) {
        this.view = view;
    }

    public void detachFromView() {
        view = null;
    }

    public void onSigninButtonPressed(String email, String password) {
        System.out.println("Ian working on it " + auth.getCurrentUser());
        auth.signin(email, password, new GritAuthCallback() {
            @Override
            public void onSuccess() {
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
