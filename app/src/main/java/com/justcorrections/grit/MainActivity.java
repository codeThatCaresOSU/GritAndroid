package com.justcorrections.grit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.justcorrections.grit.auth.AuthenticationHandler;
import com.justcorrections.grit.modules.account.LoginFragment;
import com.justcorrections.grit.modules.map.MapFragment;
import com.justcorrections.grit.modules.map.ResourceDetailFragment;
import com.justcorrections.grit.modules.mystery.MysteryFragment;
import com.justcorrections.grit.modules.signup.SignupInterests;
import com.justcorrections.grit.modules.signup.SignupNamesAge;
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

    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.main_bottom_nav);
        navigationHandler = new NavigationHandler(this);
        helper = DatabaseHelper.getInstance();
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
                    navigationHandler.navigateTo(SignupNamesAge.newInstance(new Bundle()), false);
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
            navigateTo(SignupNamesAge.newInstance(new Bundle()));
        }
    }

    public void setupFragments() {
        fragments.add(0, MapFragment.newInstance());
        fragments.add(1, LoginFragment.newInstance(null));
        fragments.add(2, SignupNamesAge.newInstance(new Bundle()));
    }

    public AuthenticationHandler getAuthHandler() {
        return this.authHandler;
    }
}
