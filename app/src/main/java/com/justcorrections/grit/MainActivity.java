package com.justcorrections.grit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.justcorrections.grit.auth.GritAuthentication;
import com.justcorrections.grit.modules.map.MapFragment;
import com.justcorrections.grit.modules.profile.ProfileViewAndEdit;
import com.justcorrections.grit.modules.savedresources.SavedResourcesFragment;
import com.justcorrections.grit.modules.signin.SigninFragment;

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
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                navigateTo(SigninFragment.newInstance(), false);
            } else {
                navigateTo(ProfileViewAndEdit.newInstance(), false);
            }
        } else if (id == R.id.menu_saved) {
            navigateTo(SavedResourcesFragment.newInstance(), false);
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
        try {
            errorText.setText(message);
            errorText.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.d("MAIN ACTIVITY", e.getMessage());
        }
    }

    /**
     * Hides the error message from the main activity.
     */
    public void hideErrorText() {
        errorText.setVisibility(View.INVISIBLE);
    }

}
