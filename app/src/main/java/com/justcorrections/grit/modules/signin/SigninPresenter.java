package com.justcorrections.grit.modules.signin;

import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.auth.GritAuthentication;
import com.justcorrections.grit.auth.GritAuthentication.GritAuthCallback;
import com.justcorrections.grit.modules.homepage.HomepageFragment;

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
        auth.signin(email, password, new GritAuthCallback() {
            @Override
            public void onSuccess() {
                ((MainActivity) view.getActivity()).navigateTo(HomepageFragment.newInstance(), true);
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
