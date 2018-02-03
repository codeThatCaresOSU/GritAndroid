package com.justcorrections.grit.auth;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ianwillis on 2/2/18.
 */

public class AuthenticationHandler {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    public AuthenticationHandler(AppCompatActivity activity) {
        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                // user logged in
//                if (firebaseAuth.getCurrentUser() != null) {
//                    fragments.remove(1);
//                    fragments.add(1, AccountFragment.newInstance());
//                }
//                // user logged out
//                else {
//                    fragments.remove(1);
//                    fragments.add(1, LoginFragment.newInstance(null));
//                }
//
//                // TODO: add a transition using anim resource files
//                if (bottomNav.getSelectedItemId() == R.id.menu_account) {
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.main_fragment_holder, fragments.get(1))
//                            .commit();
//                }

            }
        };
    }
}
