package com.justcorrections.grit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.justcorrections.grit.modules.account.AccountFragment;
import com.justcorrections.grit.modules.map.MapFragment;
import com.justcorrections.grit.modules.mystery.MysteryFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private NavigationHandler navigationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.main_bottom_nav);
        navigationHandler = new NavigationHandler(this);

        bottomNav.setOnNavigationItemSelectedListener(this);
        bottomNav.setSelectedItemId(R.id.menu_map);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_map) {
            navigateTo(MapFragment.newInstance(), false);
        } else if (id == R.id.menu_account) {
            navigateTo(AccountFragment.newInstance(), false);
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
}
