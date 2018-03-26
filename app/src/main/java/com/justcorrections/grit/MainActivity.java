package com.justcorrections.grit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.justcorrections.grit.auth.GritAuthentication;
import com.justcorrections.grit.modules.login.LoginFragment;
import com.justcorrections.grit.modules.map.MapFragment;
import com.justcorrections.grit.modules.mystery.MysteryFragment;
import com.justcorrections.grit.modules.signup.SignupNamesAge;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private NavigationHandler navigationHandler;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationHandler = new NavigationHandler(this);

        BottomNavigationView bottomNav = findViewById(R.id.main_bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(this);
        bottomNav.setSelectedItemId(R.id.menu_map);

        errorText = findViewById(R.id.error_textview);
        errorText.setVisibility(View.INVISIBLE);

    }

    public void navigateTo(Fragment fragment, boolean saveTransactionToBackStack) {
        navigationHandler.navigateTo(fragment, saveTransactionToBackStack);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_map) {
            navigateTo(MapFragment.newInstance(), false);
        } else if (id == R.id.menu_account) {
            if (GritAuthentication.getInstance().getCurrentUser() == null)
                navigateTo(LoginFragment.newInstance(), false);
            else
                System.out.println("need to add details fragment here");
        } else if (id == R.id.menu_mystery) {
            navigateTo(MysteryFragment.newInstance("param 1", "param 2"), false);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        navigationHandler.onBackPressed();
    }

    /**
     * Shows an error messsage at the top of the main activity.
     *
     * @param message The error message to show.
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
