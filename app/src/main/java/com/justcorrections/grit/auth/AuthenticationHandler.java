package com.justcorrections.grit.auth;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ianwillis on 2/2/18.
 */

public class AuthenticationHandler {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    public AuthenticationHandler(AppCompatActivity activity) {

    }
}
