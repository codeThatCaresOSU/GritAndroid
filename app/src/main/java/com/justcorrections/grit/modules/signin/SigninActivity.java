package com.justcorrections.grit.modules.signin;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.justcorrections.grit.R;
import com.justcorrections.grit.auth.GritAuth;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    private SigninPresenter presenter;

    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;

    public static SigninActivity newInstance() {
        return new SigninActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        presenter = new SigninPresenter(GritAuth.getInstance());

        emailLayout = findViewById(R.id.signin_email_layout);
        passwordLayout = findViewById(R.id.signin_password_layout);
        emailInput = findViewById(R.id.signin_email_input);
        passwordInput = findViewById(R.id.signin_password_input);
        Button signinButton = findViewById(R.id.signin_sign_in_button);
        Button signupButton = findViewById(R.id.signin_sign_up_button);
        Button forgotPasswordButton = findViewById(R.id.signin_forgot_password_button);

        System.out.println("Ianaa " + signupButton.getPaddingTop() + " " + signupButton.getPaddingRight());
        System.out.println("Ianaa " + forgotPasswordButton.getPaddingTop() + " " + forgotPasswordButton.getPaddingRight());

        signinButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);
        forgotPasswordButton.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.attachTo(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.detachFromView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin_sign_in_button:
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                presenter.onSigninButtonPressed(email, password);
                break;
            case R.id.signin_sign_up_button:
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

    public void setPasswordLayoutError(String errorMessage) {
        passwordLayout.setError(errorMessage);
    }

    public void clearErrorText() {
        emailLayout.setError("");
        passwordLayout.setError("");
    }

}
