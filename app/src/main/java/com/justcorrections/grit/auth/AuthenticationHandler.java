package com.justcorrections.grit.auth;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;

/**
 * Created by ianwillis on 2/2/18.
 */

public class AuthenticationHandler {

    private FirebaseAuth auth;

    public AuthenticationHandler(AppCompatActivity activity) {
        auth = FirebaseAuth.getInstance();
        loadCurrentUser();
    }

    private void loadCurrentUser() {

    }

    public void addAuthStateListener(AuthStateListener authStateListener) {
        auth.addAuthStateListener(authStateListener);
        AuthStateListener auth = new AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
    }

}
