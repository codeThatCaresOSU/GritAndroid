package com.justcorrections.grit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.justcorrections.grit.account.AccountFragment;
import com.justcorrections.grit.account.LoginFragment;
import com.justcorrections.grit.map.MapFragment;
import com.justcorrections.grit.mystery.MysteryFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Firebase objects
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    private BottomNavigationView bottomNav;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = (BottomNavigationView) findViewById(R.id.main_bottom_nav);

        auth = FirebaseAuth.getInstance();
        setupFragments();
        // onAuthStateChanged gets called when AuthStateListener is registered
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // user logged in
                if (firebaseAuth.getCurrentUser() != null) {
                    fragments.remove(1);
                    fragments.add(1, AccountFragment.newInstance("Parameter 1", "Parameter 2"));
                }
                // user logged out
                else {
                    fragments.remove(1);
                    fragments.add(1, LoginFragment.newInstance("Parameter 1", "Parameter 2"));
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
        fragments.add(MapFragment.newInstance("Parameter 1", "Parameter 2"));
        // dynamically change which fragment is added based on if the user is signed into Firebase
        if (auth.getCurrentUser() != null) {
            fragments.add(0, AccountFragment.newInstance("Parameter 1", "Parameter 2"));
        } else {
            fragments.add(1, LoginFragment.newInstance("Parameter 1", "Parameter 2"));
        }
        fragments.add(2, MysteryFragment.newInstance("Parameter 1", "Parameter 2"));
    }
}
