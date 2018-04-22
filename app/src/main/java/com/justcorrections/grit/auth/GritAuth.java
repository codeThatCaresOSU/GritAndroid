package com.justcorrections.grit.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

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
                FirebaseAuthException f = (FirebaseAuthException) e;
                System.out.println("Ian " + f.getMessage() + " : " + f.getErrorCode());
                if (f.getErrorCode().equals("ERROR_INVALID_EMAIL")) {
                    callback.onFailure(new GritAuthEmailException("The email address is badly formatted"));
                } else if (f.getErrorCode().equals("ERROR_USER_NOT_FOUND")) {
                    callback.onFailure(new Grit);
                }

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
                callback.onFailure(null);
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
                callback.onFailure(null);
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

    /*
     * lkasdf
     * asdf
     * -> The email address is badly formatted
     * -> FirebaseAuthInvalidCredentialsException
     *
     * ""/null
     * *
     * ->
     * -> IllegalArgumentException: Given String is empty or null
     *
     * a@a
     * a
     * -> There is no user record corresponding to this identifier. The user may have been deleted
     * -. FirebaseAuthInvalidUserException
     *
     * a@a
     * ""/null
     * ->
     * -> IllegalArgumentException: Given String is empty or null
     *
     * ian@ian.com (user exists with this email address)
     * aaaaa (wrong password)
     * -> The password is invalid or the user does not have a password
     * -> FirebaseAuthInvalidCredentialsException
     *
     * ian@dslds.com
     * *
     * -> There is no user record corresponding to this identifier. The user may have been deleted.
     * -> FirebaseAuthInvalidUserException
     *
     *
     * no providers
     *
     * ("ERROR_INVALID_CUSTOM_TOKEN", "The custom token format is incorrect. Please check the documentation."));
        ("ERROR_CUSTOM_TOKEN_MISMATCH", "The custom token corresponds to a different audience."));
        ("ERROR_INVALID_CREDENTIAL", "The supplied auth credential is malformed or has expired."));
        ("ERROR_INVALID_EMAIL", "The email address is badly formatted."));
        ("ERROR_WRONG_PASSWORD", "The password is invalid or the user does not have a password."));
        ("ERROR_USER_MISMATCH", "The supplied credentials do not correspond to the previously signed in user."));
        ("ERROR_REQUIRES_RECENT_LOGIN", "This operation is sensitive and requires recent authentication. Log in again before retrying this request."));
        ("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address."));
        ("ERROR_EMAIL_ALREADY_IN_USE", "The email address is already in use by another account."));
        ("ERROR_CREDENTIAL_ALREADY_IN_USE", "This credential is already associated with a different user account."));
        ("ERROR_USER_DISABLED", "The user account has been disabled by an administrator."));
        ("ERROR_USER_TOKEN_EXPIRED", "The user\'s credential is no longer valid. The user must sign in again."));
        ("ERROR_USER_NOT_FOUND", "There is no user record corresponding to this identifier. The user may have been deleted."));
        ("ERROR_INVALID_USER_TOKEN", "The user\'s credential is no longer valid. The user must sign in again."));
        ("ERROR_OPERATION_NOT_ALLOWED", "This operation is not allowed. You must enable this service in the console."));
        ("ERROR_WEAK_PASSWORD", "The given password is invalid."));
     */

}
