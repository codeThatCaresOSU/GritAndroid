package com.justcorrections.grit.utils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Gus on 10/2/2017.
 */

public class DatabaseHelper {

    private static final String USERS = "Users";
    private static final String OHIO_LOCATIONS = "OhioData";
    private static final String BUSINESSES = "MapBusiness";
    private static final String FIRST_NAME = "FirstName";
    private static final String Last_Name = "LastName";
    private static final String AGE = "Age";
    // private static final String INTERESTS = "Interests";

    private GritUser gritUser;

    private static FirebaseDatabase database;
    private static DatabaseReference users;
    private static DatabaseReference locations;
    private static DatabaseHelper helper;

    private DatabaseHelper() {
        database = FirebaseDatabase.getInstance();
        users = database.getReference(USERS);
    }

    public static synchronized DatabaseHelper getInstance() {
        if (helper == null) {
            helper = new DatabaseHelper();
        }

        return helper;
    }

    public void setUserData(GritUser gritUser) {
        DatabaseReference user = users.child(gritUser.getId());
        user.child(FIRST_NAME).setValue(gritUser.getFirstName());
        user.child(Last_Name).setValue(gritUser.getLastName());
        user.child(AGE).setValue(gritUser.getAge());
    }

    // TODO: possibly get the data and return it to the calling activity with an interface
    public void getUserData(GritUser gritUser, ValueEventListener callback) {
        DatabaseReference user = users.child(gritUser.getId());
        user.addListenerForSingleValueEvent(callback);
    }

    // basically allows for self implementation. Just note that getting the data is asyncronous, so
    // it won't be immediately available. At the end of the onDataChange method, put a call to the
    // method that you want to execute after the data is available.
    public void getOhioLocations(ValueEventListener callback) {
        locations.addListenerForSingleValueEvent(callback);
    }

    // adds the location to a randomly named node under the OhioData node
    public void setLocation(MapLocation location) {
        locations.push().setValue(location);
    }

}
