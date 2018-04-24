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
import com.justcorrections.grit.data.model.GritUser;
import com.justcorrections.grit.modules.profile.ProfileViewAndEdit;
import com.justcorrections.grit.modules.signin.SigninFragment;
import com.justcorrections.grit.modules.map.MapFragment;
import com.justcorrections.grit.modules.mystery.MysteryFragment;

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

        GritAuthentication.getInstance().signOut();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_map) {
            navigateTo(MapFragment.newInstance(), false);
        } else if (id == R.id.menu_account) {
            if (GritAuthentication.getInstance().getCurrentUser() == null)
                navigateTo(SigninFragment.newInstance(), false);
            else
                System.out.println("need to add details fragment here");
        } else if (id == R.id.menu_mystery) {

            // TODO remove tests for profile view
            GritUser user1 = new GritUser();
            user1.setGender("male");
            user1.setEmail("pulpdrew@gmail.com");
            user1.setPassword("qwerty123");
            user1.setAge(19);

            navigateTo(ProfileViewAndEdit.newInstance(user1), false);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        navigationHandler.onBackPressed();
    }

    public void navigateTo(Fragment fragment, boolean saveTransactionToBackStack) {
        navigationHandler.navigateTo(fragment, saveTransactionToBackStack);
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
