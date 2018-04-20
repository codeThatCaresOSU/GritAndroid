package com.justcorrections.grit.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.justcorrections.grit.R;

/**
 * Created by ianwillis on 1/31/18.
 */

public class NavigationHandler {

    private AppCompatActivity activity;

    public NavigationHandler(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void navigateTo(Fragment fragment, boolean saveTransactionToBackSack) {
        FragmentTransaction ft = activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_holder, fragment);

        if (saveTransactionToBackSack)
            ft.addToBackStack(null);

        ft.commit();
    }

    public void onBackPressed() {
        FragmentManager fm = activity.getSupportFragmentManager();
        if (fm.getBackStackEntryCount() != 0) {
            fm.popBackStack();
        }
    }

}
