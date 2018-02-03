package com.justcorrections.grit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.justcorrections.grit.modules.account.LoginFragment;
import com.justcorrections.grit.modules.map.MapFragment;
import com.justcorrections.grit.modules.mystery.MysteryFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private NavigationHandler navigationHandler;
    private CoordinatorLayout snackbarView;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigationHandler = new NavigationHandler(this);

        setContentView(R.layout.activity_main);

        snackbarView = (CoordinatorLayout) findViewById(R.id.main_snackbar_view);

        bottomNav = (BottomNavigationView) findViewById(R.id.main_bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(this);
        bottomNav.setSelectedItemId(R.id.menu_map);

//        auth = FirebaseAuth.getInstance();
//        // onAuthStateChanged gets called when AuthStateListener is registered
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                Log.d("Main Activity", "onAuthStateChanged: user state changed");
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
//
//            }
//        };
//
//        auth.addAuthStateListener(authStateListener);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_map) {
            navigateTo(MapFragment.newInstance(), false);
        } else if (id == R.id.menu_account) {
            navigateTo(LoginFragment.newInstance(null), false);
        } else if (id == R.id.menu_mystery) {
            navigateTo(MysteryFragment.newInstance("param 1", "param 2"), false);
        }
        return true;
    }

    public void navigateTo(Fragment fragment, boolean saveTransactionToBackStack) {
        navigationHandler.navigateTo(fragment, saveTransactionToBackStack);
    }

    @Override
    public void onBackPressed() {
        navigationHandler.onBackPressed();
    }

    public void showSnackBarMessage(String message, int snackbarLength) {
        Snackbar snackbar = Snackbar.make(snackbarView, message, snackbarLength);
        snackbar.show();
    }
//    @Override
//    public void onResetRequest(String email) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        status = builder.setView(R.layout.dialog_status)
//                .setCancelable(false)
//                .create();
//        status.show();
//
//        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    status.findViewById(R.id.dialog_success).setVisibility(View.VISIBLE);
//                    ((TextView) status.findViewById(R.id.dialog_status)).setText(R.string.reset_email_sent);
//                } else {
//                    status.findViewById(R.id.dialog_error).setVisibility(View.VISIBLE);
//                    ((TextView) status.findViewById(R.id.dialog_status)).setText(R.string.reset_email_failed);
//                }
//                status.findViewById(R.id.dialog_progress).setVisibility(View.INVISIBLE);
//                status.setCancelable(true);
//            }
//        });
//    }
//
//    @Override
//    public void onLoginRequest(String email, String password) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        status = builder.setView(R.layout.dialog_status)
//                .setCancelable(false)
//                .create();
//        status.show();
//        ((TextView) status.findViewById(R.id.dialog_status)).setText(R.string.signing_in);
//        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    status.dismiss();
//                } else {
//                    status.findViewById(R.id.dialog_error).setVisibility(View.VISIBLE);
//                    ((TextView) status.findViewById(R.id.dialog_status)).setText(R.string.invalid_credentials);
//                    status.findViewById(R.id.dialog_progress).setVisibility(View.INVISIBLE);
//                    status.setCancelable(true);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onCreateRequest(String email, String password) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        status = builder.setView(R.layout.dialog_status)
//                .setCancelable(false)
//                .create();
//        status.show();
//        ((TextView) status.findViewById(R.id.dialog_status)).setText(R.string.creating_account);
//        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    status.dismiss();
//                    fragments.remove(1);
//                    fragments.add(1, AccountFragment.newInstance());
//                    setSelected(1);
//                } else {
//                    status.findViewById(R.id.dialog_error).setVisibility(View.VISIBLE);
//                    ((TextView) status.findViewById(R.id.dialog_status)).setText(R.string.error_occurred);
//                    status.findViewById(R.id.dialog_progress).setVisibility(View.INVISIBLE);
//                    status.setCancelable(true);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onFail(String message) {
//        Snackbar snackbar = Snackbar.make(snackbarView, message, Snackbar.LENGTH_LONG);
//        snackbar.show();
//    }


}
