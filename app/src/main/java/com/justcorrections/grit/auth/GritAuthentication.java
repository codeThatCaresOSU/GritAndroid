package com.justcorrections.grit.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.justcorrections.grit.auth.GritUser.GritUserType;

/**
 * Created by ianwillis on 2/21/18.
 */

public class GritAuthentication {

    private static GritAuthentication INSTANCE;

    private FirebaseAuth firebaseAuth;
    private GritUser gritUser;

    public static GritAuthentication getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GritAuthentication();
        }
        return INSTANCE;
    }

    private GritAuthentication() {
        firebaseAuth = FirebaseAuth.getInstance();
        gritUser = null;
    }

    public GritUser getCurrentUser() {
        if (firebaseAuth.getCurrentUser() == null) {
            return null;
        } else if (firebaseAuth.getCurrentUser() != null && gritUser == null) {
            gritUser = new GritUser(firebaseAuth.getCurrentUser(), GritUserType.MENTEE);
            return gritUser;
        } else {
            return gritUser;
        }
    }

    public void login(String email, String password, final GritLoginListener loginListener) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    loginListener.onSuccess(getCurrentUser());
                } else {
                    loginListener.onFailure(task.getResult().toString());
                }
            }
        });
    }

    public void createUser(String email, String password, final GritCreateUserListener createUserListener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    createUserListener.onSuccess(getCurrentUser());
                } else {
                    createUserListener.onFailure(task.getResult().toString());
                }
            }
        });
    }


    public void sendPasswordResetEmail(String email, final GritPasswordResetListener passwordResetListener) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    passwordResetListener.onSuccess();
                } else {
                    passwordResetListener.onFailure(task.getResult().toString());
                }
            }
        });
    }

    public void signOut() {
        firebaseAuth.signOut();
    }

    public interface GritLoginListener {
        void onSuccess(GritUser user);

        void onFailure(String errorMessage);
    }

    public interface GritCreateUserListener {
        void onSuccess(GritUser user);

        void onFailure(String errorMessage);
    }

    public interface GritPasswordResetListener {
        void onSuccess();

        void onFailure(String errorMessage);
    }

}
