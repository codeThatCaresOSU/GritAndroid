package com.justcorrections.grit.auth;

import com.google.firebase.auth.FirebaseUser;
import com.justcorrections.grit.auth.AuthHandler.FirebaseLoginListener;

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

    public void signOut() {
        auth.signOut();
        firebaseUser = null;
    }

    public interface GritLoginListener {
        void onSuccess(GritUser user);
        void onFailure(String errorMessage);
    }

}
