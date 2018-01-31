package com.justcorrections.grit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ianwillis on 1/31/18.
 */

public class NavigationHandler {

    private AppCompatActivity activity;

    public NavigationHandler(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void navigateTo(Fragment fragment) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_holder, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void onBackPressed() {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStack();
        }
    }
}
