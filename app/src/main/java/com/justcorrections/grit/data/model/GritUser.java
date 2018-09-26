package com.justcorrections.grit.data.model;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Andrew Davis on 4/21/2018.
 */

public class GritUser extends FirebaseDataModel {

    private String birthday;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String zip;
    private String bio;
    private String email;
    private String password;
    private String gender;
    private boolean isMentor;
    private ArrayList<String> interests;

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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

    public boolean isMentor() {
        return isMentor;
    }

    public void setMentor(boolean mentor) {
        isMentor = mentor;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    /*
     * Bundle Argument keys
     */
    public static final String FIRST_NAME_KEY = "firstname";
    public static final String LAST_NAME_KEY = "lastname";
    public static final String CITY_KEY = "city";
    public static final String ADDRESS_KEY = "address";
    public static final String ZIP_KEY = "zip";
    public static final String BIO_KEY = "bio";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";
    public static final String BIRTHDAY_KEY = "birthday";
    public static final String GENDER_KEY = "gender";
    public static final String MENTOR_KEY = "mentor";
    public static final String INTERESTS_KEY = "interests";

    public static GritUser readFromBundle(Bundle bundle, Context context) {

        GritUser user = new GritUser();

        user.setFirstName(bundle.getString(FIRST_NAME_KEY, ""));
        user.setLastName(bundle.getString(LAST_NAME_KEY, ""));
        user.setCity(bundle.getString(CITY_KEY, ""));
        user.setAddress(bundle.getString(ADDRESS_KEY, ""));
        user.setZip(bundle.getString(ZIP_KEY, ""));
        user.setBio(bundle.getString(BIO_KEY, ""));
        user.setEmail(bundle.getString(EMAIL_KEY, ""));
        user.setPassword(bundle.getString(PASSWORD_KEY, ""));
        user.setBirthday(bundle.getString(BIRTHDAY_KEY, ""));
        user.setGender(bundle.getString(GENDER_KEY, ""));
        user.setMentor(bundle.getBoolean(MENTOR_KEY, false));

        user.interests = bundle.getStringArrayList(INTERESTS_KEY);
        if (user.interests == null) {
            user.interests = new ArrayList<>();
        }

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
        bundle.putString(BIO_KEY, user.getBio());
        bundle.putString(PASSWORD_KEY, user.getPassword());
        bundle.putString(GENDER_KEY, user.getGender());
        bundle.putBoolean(MENTOR_KEY, user.isMentor());
        bundle.putStringArrayList(INTERESTS_KEY, user.getInterests());

        // return the bundle
        return bundle;
    }

    public static void saveToDatabase(GritUser user, String uid) {



    }
}
