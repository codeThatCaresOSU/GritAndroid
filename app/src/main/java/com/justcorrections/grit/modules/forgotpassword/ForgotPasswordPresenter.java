package com.justcorrections.grit.modules.forgotpassword;

import com.justcorrections.grit.auth.GritAuth;

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

}
