package com.justcorrections.grit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.justcorrections.grit.auth.GritAuth;
import com.justcorrections.grit.modules.hompage.HomepageActivity;
import com.justcorrections.grit.modules.signin.SigninActivity;

public class SplashActivity extends AppCompatActivity {

    private GritAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = GritAuth.getInstance();

        switchToCorrectActivity();
    }

    private void switchToCorrectActivity() {
        // check if a user is current signed in
        if (auth.getCurrentUser() == null) {
            // no user currently signed in
            startSigninActivity();
        } else {
            // a user is currently signed in
            startHomepageActivity();
        }

    }

    private void startSigninActivity() {
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
        finish();
    }

    private void startHomepageActivity() {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
        finish();
    }
}
