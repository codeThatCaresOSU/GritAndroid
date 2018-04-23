package com.justcorrections.grit.modules.forgotpassword;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import com.justcorrections.grit.auth.GritAuth;
import com.justcorrections.grit.modules.signin.SigninActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ForgotPasswordPresenter presenter;

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
        //setContentView(R.layout.signin_activity);

        presenter = new ForgotPasswordPresenter(GritAuth.getInstance());

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

}
