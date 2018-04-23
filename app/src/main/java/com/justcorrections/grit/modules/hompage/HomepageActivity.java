package com.justcorrections.grit.modules.hompage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.justcorrections.grit.utils.NavigationHandler;
import com.justcorrections.grit.R;
import com.justcorrections.grit.modules.signin.SigninActivity;

public class HomepageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private NavigationHandler navigationHandler;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
//        navigationHandler = new NavigationHandler(this);
//
//        BottomNavigationView bottomNav = findViewById(R.id.main_bottom_nav);
//        bottomNav.setOnNavigationItemSelectedListener(this);
//        bottomNav.setSelectedItemId(R.id.menu_map);
//
//        errorText = findViewById(R.id.error_textview);
//        errorText.setVisibility(View.INVISIBLE);
//
//        GritAuth.getInstance().signOut();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
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
