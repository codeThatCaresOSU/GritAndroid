package com.justcorrections.grit.modules.signin;

import android.content.Intent;
import android.text.TextUtils;

import com.justcorrections.grit.auth.GritAuth;
import com.justcorrections.grit.auth.GritAuth.GritAuthCallback;
import com.justcorrections.grit.modules.forgotpassword.ForgotPasswordActivity;
import com.justcorrections.grit.modules.homepage.HomepageActivity;
import com.justcorrections.grit.modules.signup.SignupActivity;

/**
 * Created by ianwillis on 3/9/18.
 */

public class SigninPresenter {

    private SigninActivity view;
    private GritAuth auth;

    public SigninPresenter(GritAuth auth) {
        this.auth = auth;
    }

    public void attachTo(SigninActivity view) {
        this.view = view;
    }

    public void detachFromView() {
        view = null;
    }

    public boolean isAttachedToView() {
        return view != null;
    }

    public void onSigninButtonPressed(String email, String password) {
        view.clearErrorText();
        if (TextUtils.isEmpty(email)) {
            view.setEmailInputError("Email can't be empty");
        } else if (TextUtils.isEmpty(password)) {
            view.setPasswordLayoutError("Password can't be empty");
        } else {
            auth.signin(email, password, new GritAuthCallback() {
                @Override
                public void onSuccess() {
                    if (isAttachedToView()) {
                        navigateToHomepageActivity();
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    if (isAttachedToView()) {
                        view.setEmailInputError(e.getMessage());
                    }
                }
            });
        }
    }

    public void onSignupButtonPressed() {
        navigateToSigninActivity();
    }

    public void onForgotPasswordButtonPressed() {
        navigateToForgotPasswordActivity();
    }

    private void navigateToHomepageActivity() {
        Intent intent = new Intent(view, HomepageActivity.class);
        view.startActivity(intent);
        view.finish();
    }

    private void navigateToSigninActivity() {
        Intent intent = new Intent(view, SignupActivity.class);
        view.startActivity(intent);
    }

    private void navigateToForgotPasswordActivity() {
        Intent intent = new Intent(view, ForgotPasswordActivity.class);
        view.startActivity(intent);
    }
}
