package com.justcorrections.grit.auth;

import com.google.firebase.auth.FirebaseUser;
import com.justcorrections.grit.auth.AuthHandler.FirebaseCreateUserListener;
import com.justcorrections.grit.auth.AuthHandler.FirebaseLoginListener;
import com.justcorrections.grit.auth.AuthHandler.FirebasePasswordResetListener;

/**
 * Created by ianwillis on 2/21/18.
 */

public class GritUser {

    private static GritUser INSTANCE;

    private AuthHandler auth;
    private FirebaseUser firebaseUser;

    public static GritUser getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GritUser();
        }
        return INSTANCE;
    }

    public GritUser() {
        auth = AuthHandler.getInstance();
        firebaseUser = auth.getCurrentUser();
    }

    public boolean isLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    public void login(String email, String password, final GritLoginListener gritLoginListener) {
        auth.login(email, password, new FirebaseLoginListener() {
            @Override
            public void onSuccess(FirebaseUser user) {
                firebaseUser = user;
                gritLoginListener.onSuccess(GritUser.this);
            }

            @Override
            public void onFailure(String errorMessage) {
                gritLoginListener.onFailure(errorMessage);
            }
        });
    }


    public interface GritLoginListener {
        void onSuccess(GritUser user);

        void onFailure(String errorMessage);
    }

    public void signOut() {
        auth.signOut();
        firebaseUser = null;
    }

    public void createUser(String email, String password, final GritCreateUserListener createUserListener) {
        auth.createUser(email, password, new FirebaseCreateUserListener() {
            @Override
            public void onSuccess(FirebaseUser user) {
                firebaseUser = user;
                createUserListener.onSuccess(GritUser.this);
            }

            @Override
            public void onFailure(String errorMessage) {
                createUserListener.onFailure(errorMessage);
            }
        });
    }

    public interface GritCreateUserListener {
        void onSuccess(GritUser user);

        void onFailure(String errorMessage);
    }

    public void sendPasswordResetEmail(String email, final GritPasswordResetListener passwordResetListener) {
        auth.sendPasswordResetEmail(email, new FirebasePasswordResetListener() {
            @Override
            public void onSuccess() {
                passwordResetListener.onSuccess();
            }

            @Override
            public void onFailure(String errorMessage) {
                passwordResetListener.onFailure(errorMessage);
            }
        });
    }

    public interface GritPasswordResetListener {
        void onSuccess();

        void onFailure(String errorMessage);
    }

    public void addOnLoginStateChangeListener(OnLoginStateChangeListener onLoginStateChangeListener) {

    }

    public interface OnLoginStateChangeListener {

    }
}
