package com.justcorrections.grit.modules.signin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.justcorrections.grit.R;
import com.justcorrections.grit.auth.GritAuthentication;

public class SigninFragment extends Fragment implements View.OnClickListener {

    private SigninPresenter presenter;

    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;

    public static SigninFragment newInstance() {
        return new SigninFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SigninPresenter(GritAuthentication.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailLayout = view.findViewById(R.id.signin_email_layout);
        passwordLayout = view.findViewById(R.id.signin_password_layout);
        emailInput = view.findViewById(R.id.signin_email_input);
        passwordInput = view.findViewById(R.id.signin_password_input);
        Button signinButton = view.findViewById(R.id.signin_signin_button);
        Button createAccountButton = view.findViewById(R.id.signin_create_account_button);

        emailInput.setOnClickListener(this);
        passwordInput.setOnClickListener(this);
        signinButton.setOnClickListener(this);
        createAccountButton.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachTo(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin_signin_button:
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                presenter.onSigninButtonPressed(email, password);
                break;
            case R.id.signin_create_account_button:
                presenter.onCreateAccountButtonPressed();
                break;
            case R.id.signin_forgot_password_button:
                presenter.onForgotPasswordButtonPressed();
                break;
        }
    }

    public void setEmailInputError(String errorMessage) {
        emailLayout.setError(errorMessage);
    }


    @Override
    public void onPause() {
        super.onPause();
        presenter.detachFromView();
    }
}
