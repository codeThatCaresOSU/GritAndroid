package com.justcorrections.grit.data.model;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.justcorrections.grit.data.DatabaseHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrew Davis on 4/21/2018.
 */

public class GritUser extends FirebaseDataModel {


    /**
     * Keys for the Data base and the data Map
     */
    public static final String NAME_KEY = "Name";
    public static final String CITY_KEY = "City";
    public static final String ADDRESS_KEY = "Street Address";
    public static final String ZIP_KEY = "Zip Code";
    public static final String DESCRIPTION_KEY = "Description";
    public static final String EMAIL_KEY = "Email";
    public static final String PASSWORD_KEY = "Password";
    public static final String BIRTHDAY_KEY = "Age";
    public static final String GENDER_KEY = "Gender";
    public static final String STATE_KEY = "State";
    public static final String OCCUPATION_KEY = "Occupation";

    /**
     * Values for Gender
     */
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";
    public static final String OTHER_PREFIX = "Other_";

    private Map<String, String> data;
    private static final String[] keys = {NAME_KEY, CITY_KEY, ADDRESS_KEY, ZIP_KEY, DESCRIPTION_KEY,
            EMAIL_KEY, PASSWORD_KEY, BIRTHDAY_KEY, GENDER_KEY, STATE_KEY, OCCUPATION_KEY};

    public GritUser() {
        data = new HashMap<>();
        for (String key : keys) {
            data.put(key, "");
        }
    }

    public String getValue(String key) {

        String value = data.get(key);

        if (key.equals(GENDER_KEY) && value.startsWith(OTHER_PREFIX)) {
                value = value.substring(OTHER_PREFIX.length());
        }

        return value;
    }

    public void setValue(String key, String value) {

        if (key.equals(GENDER_KEY)) {
            if (!value.equals(MALE) && !value.equals(FEMALE)) {
                value = OTHER_PREFIX + value;
            }
        }

        data.put(key, value);

    }

    public Map<String, String> getData() {
        return data;
    }

    public static GritUser readFromBundle(Bundle bundle) {

        GritUser user = new GritUser();

        for (String key : keys) {
            user.setValue(key, bundle.getString(key, ""));
        }

        return user;

    }

    public static Bundle writeToBundle(GritUser user) {
        Bundle bundle = new Bundle();

        for (String key : keys) {
            bundle.putString(key, user.getValue(key));
        }

        return bundle;
    }

    public static void saveToDatabase(GritUser user, String uid) {

        DatabaseReference userRef = DatabaseHelper.getReference(DatabaseHelper.DatabasePath.TEST).child(uid);

        for (String key : keys) {
            if (!key.equals(PASSWORD_KEY)) {
                userRef.child(key).setValue(user.getValue(key));
            }
        }

    }
}
