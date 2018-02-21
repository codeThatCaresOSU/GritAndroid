package com.justcorrections.grit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.justcorrections.grit.auth.AuthenticationHandler;
import com.justcorrections.grit.modules.account.LoginFragment;
import com.justcorrections.grit.modules.map.MapFragment;
import com.justcorrections.grit.modules.map.ResourceDetailFragment;
import com.justcorrections.grit.modules.mystery.MysteryFragment;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NavigationHandler navigationHandler;
    private AuthenticationHandler authHandler;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private BottomNavigationView bottomNav;
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.main_bottom_nav);
        navigationHandler = new NavigationHandler(this);
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

    public void navigateTo(Fragment fragment, boolean saveTransactionToBackStack) {
        navigationHandler.navigateTo(fragment, true);
    }

    @Override
    public void onBackPressed() {
        navigationHandler.onBackPressed();
    }

    public void setSelected(int position) {
        if (position == 0) {
            navigateTo(MapFragment.newInstance(), false);
        } else if (position == 1) {
            navigateTo(LoginFragment.newInstance(null), false);
        } else {
            navigateTo(MysteryFragment.newInstance("a", "b"), false);
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

    public void showResourceDetailFragment(String resourceID) {
        fragments.add(3, ResourceDetailFragment.newInstance(resourceID));
        setSelected(3);
    }
}
