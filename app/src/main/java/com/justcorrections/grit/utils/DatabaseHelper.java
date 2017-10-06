package com.justcorrections.grit.utils;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Gus on 10/2/2017.
 */

public class DatabaseHelper {

    private FirebaseDatabase database;
    private DatabaseReference users;

    // variables
    public DatabaseHelper() {
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
    }


    public void initializeUser(FirebaseUser gritUser) {

    }

    public void setUserData(GritUser gritUser) {

    }

}
