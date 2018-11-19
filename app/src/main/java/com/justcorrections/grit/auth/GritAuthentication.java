package com.justcorrections.grit.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
                callback.onFailure(e.getMessage());
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
                callback.onFailure(e.getMessage());
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
                callback.onFailure(e.getMessage());
            }
        });
    }

    public void signOut() {
        firebaseAuth.signOut();
    }

    public interface GritAuthCallback {
        void onSuccess();

        void onFailure(String errorMessage);
    }

}
