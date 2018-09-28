package com.justcorrections.grit.modules.signin;

import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.auth.GritAuthentication;
import com.justcorrections.grit.auth.GritAuthentication.GritAuthCallback;
import com.justcorrections.grit.modules.profile.ProfileViewAndEdit;
import com.justcorrections.grit.modules.signup.SignupEmailPasswords;
import com.justcorrections.grit.modules.signup.SignupNamesAge;

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
                ((MainActivity) view.getActivity()).navigateTo(ProfileViewAndEdit.newInstance(), false);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.setEmailInputError(errorMessage);
            }
        });
    }

    public void onCreateAccountButtonPressed() {
        ((MainActivity) view.getActivity()).navigateTo(SignupNamesAge.newInstance(view.getArguments()), true);
    }

    public void onForgotPasswordButtonPressed() {

    }
}
