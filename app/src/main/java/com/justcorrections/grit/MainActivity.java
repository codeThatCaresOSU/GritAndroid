package com.justcorrections.grit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.justcorrections.grit.auth.AuthenticationHandler;
import com.justcorrections.grit.modules.account.AccountFragment;
import com.justcorrections.grit.modules.account.LoginFragment;
import com.justcorrections.grit.modules.map.MapFragment;
import com.justcorrections.grit.modules.mystery.MysteryFragment;
import com.justcorrections.grit.utils.DatabaseHelper;
import com.justcorrections.grit.utils.NavigationHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NavigationHandler navigationHandler;
    private AuthenticationHandler authHandler;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private BottomNavigationView bottomNav;
    private List<Fragment> fragments = new ArrayList<>();
    private AlertDialog status;
    private TextView errorText;


    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.main_bottom_nav);
        navigationHandler = new NavigationHandler(this);
        helper = DatabaseHelper.getInstance();

        errorText = (TextView) findViewById(R.id.error_textview);
        errorText.setVisibility(View.INVISIBLE);

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

        authHandler = new AuthenticationHandler(this, this.fragments);
        setupFragments();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_map) {
                    navigationHandler.navigateTo(MapFragment.newInstance(), false);
                } else if (id == R.id.menu_account) {
                    navigationHandler.navigateTo(LoginFragment.newInstance(null), false);
                } else if (id == R.id.menu_mystery) {
                    navigationHandler.navigateTo(MysteryFragment.newInstance("param 1", "param 2"), false);
                }
                return true;
            }
        });
        bottomNav.setSelectedItemId(R.id.menu_map);
    }

    public void navigateTo(Fragment fragment) {
        navigationHandler.navigateTo(fragment, true);
    }

    @Override
    public void onBackPressed() {
        navigationHandler.onBackPressed();
    }

    public void setSelected(int position) {
        if (position == 0) {
            navigateTo(MapFragment.newInstance());
        } else if (position == 1) {
            navigateTo(LoginFragment.newInstance(null));
        } else {
            navigateTo(MysteryFragment.newInstance("a", "b"));
        }
    }

    public void setupFragments() {
        fragments.add(0, MapFragment.newInstance());
        fragments.add(1, LoginFragment.newInstance(null));
        fragments.add(2, MysteryFragment.newInstance("Parameter 1", "Parameter 2"));
    }

    public AuthenticationHandler getAuthHandler() {
        return this.authHandler;
    }

    /**
     * Shows an error messsage at the top of the main activity.
     * @param message
     *          The error message to show.
     */
    public void showErrorText(String message) {
        errorText.setText(message);
        errorText.setVisibility(View.VISIBLE);
    }

    /**
     * Hides the error message from the main activity.
     */
    public void hideErrorText() {
        errorText.setVisibility(View.INVISIBLE);
    }

}
