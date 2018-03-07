package com.justcorrections.grit.auth;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;
import com.justcorrections.grit.modules.account.AccountFragment;
import com.justcorrections.grit.modules.account.LoginFragment;
import com.justcorrections.grit.modules.account.OnAccountRequestListener;

import java.util.List;

/**
 * Created by ianwillis on 2/2/18.
 */

public class AuthenticationHandler implements OnAccountRequestListener {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AppCompatActivity activity;
    private AlertDialog status;
    private BottomNavigationView bottomNav;
    private CoordinatorLayout snackbarView;
    private List<Fragment> fragments;

    public AuthenticationHandler(AppCompatActivity activity, List<Fragment> fragments) {
        this.activity = activity;
        this.snackbarView = activity.findViewById(R.id.main_snackbar_view);
        this.fragments = fragments;
        this.bottomNav = activity.findViewById(R.id.main_bottom_nav);

        setupFirebaseStuff();
    }

    private void setupFirebaseStuff() {
        auth = FirebaseAuth.getInstance();
        // onAuthStateChanged gets called when AuthStateListener is registered
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d("Main Activity", "onAuthStateChanged: user state changed");
                // user logged in
                if (firebaseAuth.getCurrentUser() != null) {
                    fragments.remove(1);
                    fragments.add(1, AccountFragment.newInstance());
                }
                // user logged out
                else {
                    fragments.remove(1);
                    fragments.add(1, LoginFragment.newInstance(null));
                }

                // TODO: add a transition using anim resource files
                if (bottomNav.getSelectedItemId() == R.id.menu_account) {
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_holder, fragments.get(1))
                            .commit();
                }

            }
        };

        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onResetRequest(String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        status = builder.setView(R.layout.dialog_status)
                .setCancelable(false)
                .create();
        status.show();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    status.findViewById(R.id.dialog_success).setVisibility(View.VISIBLE);
                    ((TextView) status.findViewById(R.id.dialog_status)).setText(R.string.reset_email_sent);
                } else {
                    status.findViewById(R.id.dialog_error).setVisibility(View.VISIBLE);
                    ((TextView) status.findViewById(R.id.dialog_status)).setText(R.string.reset_email_failed);
                }
                status.findViewById(R.id.dialog_progress).setVisibility(View.INVISIBLE);
                status.setCancelable(true);
            }
        });
    }

    @Override
    public void onLoginRequest(String email, String password) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        status = builder.setView(R.layout.dialog_status)
                .setCancelable(false)
                .create();
        status.show();
        ((TextView) status.findViewById(R.id.dialog_status)).setText(R.string.signing_in);
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    status.dismiss();
                } else {
                    status.findViewById(R.id.dialog_error).setVisibility(View.VISIBLE);
                    ((TextView) status.findViewById(R.id.dialog_status)).setText(R.string.invalid_credentials);
                    status.findViewById(R.id.dialog_progress).setVisibility(View.INVISIBLE);
                    status.setCancelable(true);
                }
            }
        });
    }

    @Override
    public void onCreateRequest(String email, String password) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        status = builder.setView(R.layout.dialog_status)
                .setCancelable(false)
                .create();
        status.show();
        ((TextView) status.findViewById(R.id.dialog_status)).setText(R.string.creating_account);
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    status.dismiss();
                    fragments.remove(1);
                    fragments.add(1, AccountFragment.newInstance());
                    ((MainActivity) activity).setSelected(1);
                } else {
                    status.findViewById(R.id.dialog_error).setVisibility(View.VISIBLE);
                    ((TextView) status.findViewById(R.id.dialog_status)).setText(R.string.error_occurred);
                    status.findViewById(R.id.dialog_progress).setVisibility(View.INVISIBLE);
                    status.setCancelable(true);
                }
            }
        });
    }

    @Override
    public void onFail(String message) {
        Snackbar snackbar = Snackbar.make(snackbarView, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
