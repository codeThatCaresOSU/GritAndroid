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
    private String firstName;
    private String lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    /*
     * Bundle Argument keys
     */
    private static final String FIRST_NAME_KEY = "firstname";
    private static final String LAST_NAME_KEY = "lastname";
    private static final String CITY_KEY = "city";
    private static final String ADDRESS_KEY = "address";
    private static final String ZIP_KEY = "zip";
    private static final String BIO_KEY = "description";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String BIRTHDAY_KEY = "birthday";
    private static final String GENDER_KEY = "gender";
    private static final String STATE_KEY = "state";
    private static final String OCCUPATION_KEY = "occupation";

    public static GritUser readFromBundle(Bundle bundle, Context context) {

        GritUser user = new GritUser();

        user.setFirstName(bundle.getString(FIRST_NAME_KEY, ""));
        user.setLastName(bundle.getString(LAST_NAME_KEY, ""));
        user.setCity(bundle.getString(CITY_KEY, ""));
        user.setAddress(bundle.getString(ADDRESS_KEY, ""));
        user.setZip(bundle.getString(ZIP_KEY, ""));
        user.setDescription(bundle.getString(BIO_KEY, ""));
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
        bundle.putString(FIRST_NAME_KEY, user.getFirstName());
        bundle.putString(LAST_NAME_KEY, user.getLastName());
        bundle.putString(EMAIL_KEY, user.getEmail());
        bundle.putString(CITY_KEY, user.getCity());
        bundle.putString(ADDRESS_KEY, user.getAddress());
        bundle.putString(ZIP_KEY, user.getZip());
        bundle.putString(BIO_KEY, user.getDescription());
        bundle.putString(PASSWORD_KEY, user.getPassword());
        bundle.putString(GENDER_KEY, user.getGender());
        bundle.putString(STATE_KEY, user.getState());
        bundle.putString(OCCUPATION_KEY, user.getOccupation());

        // return the bundle
        return bundle;
    }

    public static void saveToDatabase(GritUser user, String uid) {

        DatabaseReference userRef = DatabaseHelper.getReference(DatabaseHelper.DatabasePath.TEST).child(uid);
        userRef.child("Age").setValue(user.birthday);
        userRef.child("Name").setValue(user.firstName + " " + user.lastName);
        userRef.child("Gender").setValue(user.gender);
        userRef.child("Street Address").setValue(user.address);
        userRef.child("City").setValue(user.city);
        userRef.child("State").setValue(user.state);
        userRef.child("Zip Code").setValue(user.zip);
        userRef.child("Occupation").setValue(user.occupation);
        userRef.child("Description").setValue(user.description);

    }
}
