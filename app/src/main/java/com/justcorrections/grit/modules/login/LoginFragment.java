package com.justcorrections.grit.modules.login;

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

public class LoginFragment extends Fragment implements View.OnClickListener {

    private LoginPresenter presenter;

    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter(GritAuthentication.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailLayout = view.findViewById(R.id.login_email_layout);
        passwordLayout = view.findViewById(R.id.login_password_layout);
        emailInput = view.findViewById(R.id.login_email_input);
        passwordInput = view.findViewById(R.id.login_password_input);
        Button loginButton = view.findViewById(R.id.login_login_button);
        Button createAccountButton = view.findViewById(R.id.login_create_account_button);

        emailInput.setOnClickListener(this);
        passwordInput.setOnClickListener(this);
        loginButton.setOnClickListener(this);
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
            case R.id.login_login_button:
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                presenter.onLoginButtonPressed(email, password);
                break;
            case R.id.login_create_account_button:
                presenter.onCreateAccountButtonPressed();
                break;
            case R.id.login_forgot_password_button:
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
