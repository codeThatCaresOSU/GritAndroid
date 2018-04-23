package com.justcorrections.grit.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ianwillis on 2/21/18.
 */

public class GritAuth {

    private static GritAuth INSTANCE;

    private FirebaseAuth firebaseAuth;
    private GritUser gritUser;

    public static GritAuth getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GritAuth();
        }
        return INSTANCE;
    }

    private GritAuth() {
        firebaseAuth = FirebaseAuth.getInstance();
        gritUser = null;
    }

    public GritUser getCurrentUser() {
        if (firebaseAuth.getCurrentUser() != null && gritUser == null)
            gritUser = new GritUser(firebaseAuth.getCurrentUser());

        return gritUser;

    }

    public void signin(String email, String password, final GritAuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                callback.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(new Exception("Invalid email or password", e));
            }
        });
    }

    public void createUser(String email, String password, final GritAuthCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                callback.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(new Exception("Error creating user", e));
            }
        });
    }


    public void sendPasswordResetEmail(String email, final GritAuthCallback callback) {
        firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(new Exception("Error sending password reset email", e));
            }
        });
    }

    public void signOut() {
        firebaseAuth.signOut();
    }

    public interface GritAuthCallback {
        void onSuccess();

        void onFailure(Exception e);
    }

}
