package com.justcorrections.grit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.justcorrections.grit.account.AccountFragment;
import com.justcorrections.grit.account.LoginFragment;
import com.justcorrections.grit.account.OnAccountRequestListener;
import com.justcorrections.grit.map.MapFragment;
import com.justcorrections.grit.mystery.MysteryFragment;
import com.justcorrections.grit.utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnAccountRequestListener {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private BottomNavigationView bottomNav;
    private CoordinatorLayout snackbarView;
    private List<Fragment> fragments = new ArrayList<>();
    private AlertDialog status;

    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = (BottomNavigationView) findViewById(R.id.main_bottom_nav);
        snackbarView = (CoordinatorLayout) findViewById(R.id.main_snackbar_view);
        helper = DatabaseHelper.getInstance();

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
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_holder, fragments.get(1))
                            .commit();
                }

            }
        };

        auth.addAuthStateListener(authStateListener);

        setupFragments();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_map:
                        setSelected(0);
                        break;
                    case R.id.menu_account:
                        setSelected(1);
                        break;
                    case R.id.menu_mystery:
                        setSelected(2);
                        break;
                }

                return true;
            }
        });

        setSelected(0);
    }

    private void setSelected(int position) {
        // TODO: add a nice transition using anim resource files
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_holder, fragments.get(position))
                .commit();
    }

    public void setupFragments() {
        fragments.add(0, MapFragment.newInstance());
        fragments.add(1, LoginFragment.newInstance(null));
        fragments.add(2, MysteryFragment.newInstance("Parameter 1", "Parameter 2"));
    }

    @Override
    public void onResetRequest(String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                    setSelected(1);
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
