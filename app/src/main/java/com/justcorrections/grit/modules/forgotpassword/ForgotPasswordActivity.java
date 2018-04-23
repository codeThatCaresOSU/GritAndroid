package com.justcorrections.grit.modules.forgotpassword;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.justcorrections.grit.R;
import com.justcorrections.grit.auth.GritAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements OnClickListener {

    private ForgotPasswordPresenter presenter;

    private TextInputLayout emailLayout;
    private TextInputEditText emailInput;

    public static ForgotPasswordActivity newInstance() {
        return new ForgotPasswordActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword_activity);

        presenter = new ForgotPasswordPresenter(GritAuth.getInstance());

        emailLayout = findViewById(R.id.forgotpassword_email_layout);
        emailInput = findViewById(R.id.forgotpassword_email_input);
        Button resetButton = findViewById(R.id.forgotpassword_reset_button);

        resetButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.forgotpassword_reset_button) {
            String email = emailInput.getText().toString();
            presenter.onResetButtonClicked(email);
        }
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

    public void notifyEmailSent() {
        Toast toast = Toast.makeText(this, "Reset email sent", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void hideSoftKeyboard() {
        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && inputMethodManager.isActive()) {
            if (getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public void setEmailLayoutErrorText(String error) {
        emailLayout.setError(error);
    }
}
