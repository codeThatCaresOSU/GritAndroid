package com.justcorrections.grit.modules.forgotpassword;

import android.text.TextUtils;

import com.justcorrections.grit.auth.GritAuth;
import com.justcorrections.grit.auth.GritAuth.GritAuthCallback;

public class ForgotPasswordPresenter {

    private ForgotPasswordActivity view;
    private GritAuth auth;

    public ForgotPasswordPresenter(GritAuth auth) {
        this.auth = auth;
    }

    public void attachTo(ForgotPasswordActivity view) {
        this.view = view;
    }

    public void detachFromView() {
        view = null;
    }

    public boolean isAttachedToView() {
        return view != null;
    }

    public void onResetButtonClicked(String email) {
        if (TextUtils.isEmpty(email)) {
            view.setEmailLayoutErrorText("Email can't be empty");
        } else {
            auth.sendPasswordResetEmail(email, new GritAuthCallback() {
                @Override
                public void onSuccess() {
                    view.notifyEmailSent();
                    view.hideSoftKeyboard();
                    view.finish();
                }

                @Override
                public void onFailure(Exception e) {
                    view.setEmailLayoutErrorText(e.getMessage());
                }
            });
        }
    }

}
