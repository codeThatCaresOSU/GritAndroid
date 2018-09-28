package com.justcorrections.grit.data.model;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.justcorrections.grit.data.DatabaseHelper;

/**
 * Created by Andrew Davis on 4/21/2018.
 */

public class GritUser extends FirebaseDataModel {

    private String birthday;
    private String name;
    private String city;
    private String state;
    private String address;
    private String zip;
    private String description;
    private String email;
    private String password;
    private String gender;
    private String occupation;

    public GritUser() {

    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String age) {
        this.birthday = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /* Data base and bundle keys */
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

    public static GritUser readFromBundle(Bundle bundle, Context context) {

        GritUser user = new GritUser();

        user.setName(bundle.getString(NAME_KEY, ""));
        user.setCity(bundle.getString(CITY_KEY, ""));
        user.setAddress(bundle.getString(ADDRESS_KEY, ""));
        user.setZip(bundle.getString(ZIP_KEY, ""));
        user.setDescription(bundle.getString(DESCRIPTION_KEY, ""));
        user.setEmail(bundle.getString(EMAIL_KEY, ""));
        user.setPassword(bundle.getString(PASSWORD_KEY, ""));
        user.setBirthday(bundle.getString(BIRTHDAY_KEY, ""));
        user.setGender(bundle.getString(GENDER_KEY, ""));
        user.setState(bundle.getString(STATE_KEY, ""));
        user.setOccupation(bundle.getString(OCCUPATION_KEY, ""));

        return user;

    }

    public static Bundle writeToBundle(GritUser user, Context context) {
        Bundle bundle = new Bundle();

        // Put all the info in the bundle
        bundle.putString(BIRTHDAY_KEY, user.getBirthday());
        bundle.putString(NAME_KEY, user.getName());
        bundle.putString(EMAIL_KEY, user.getEmail());
        bundle.putString(CITY_KEY, user.getCity());
        bundle.putString(ADDRESS_KEY, user.getAddress());
        bundle.putString(ZIP_KEY, user.getZip());
        bundle.putString(DESCRIPTION_KEY, user.getDescription());
        bundle.putString(PASSWORD_KEY, user.getPassword());
        bundle.putString(GENDER_KEY, user.getGender());
        bundle.putString(STATE_KEY, user.getState());
        bundle.putString(OCCUPATION_KEY, user.getOccupation());

        // return the bundle
        return bundle;
    }

    public static void saveToDatabase(GritUser user, String uid) {

        DatabaseReference userRef = DatabaseHelper.getReference(DatabaseHelper.DatabasePath.TEST).child(uid);
        userRef.child(BIRTHDAY_KEY).setValue(user.birthday);
        userRef.child(NAME_KEY).setValue(user.name);
        userRef.child(GENDER_KEY).setValue(user.gender);
        userRef.child(ADDRESS_KEY).setValue(user.address);
        userRef.child(CITY_KEY).setValue(user.city);
        userRef.child(STATE_KEY).setValue(user.state);
        userRef.child(ZIP_KEY).setValue(user.zip);
        userRef.child(OCCUPATION_KEY).setValue(user.occupation);
        userRef.child(DESCRIPTION_KEY).setValue(user.description);

    }
}
