package com.justcorrections.grit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.justcorrections.grit.account.AccountFragment;
import com.justcorrections.grit.map.MapFragment;
import com.justcorrections.grit.mystery.MysteryFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = (BottomNavigationView) findViewById(R.id.main_bottom_nav);

        // TODO: change these parameters as needed
        fragments.add(MapFragment.newInstance("Parameter 1", "Parameter 2"));
        fragments.add(AccountFragment.newInstance("Parameter 1", "Parameter 2"));
        fragments.add(MysteryFragment.newInstance("Parameter 1", "Parameter 2"));

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
}
