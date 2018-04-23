package com.justcorrections.grit.modules.signup;

import android.content.Context;
import android.os.Bundle;

import com.justcorrections.grit.R;

import java.util.ArrayList;

/**
 * Created by pulpd on 3/20/2018.
 */

public class SignupInfo {

    public int age;
    public String firstName, lastName, city, address, zip, bio, email, password, gender;
    public boolean isMentor;
    public ArrayList<String> interests;

    public SignupInfo() {
        interests = new ArrayList<>();
    }

    public static SignupInfo readFromBundle(Bundle bundle, Context context) {

        SignupInfo signupInfo = new SignupInfo();

        signupInfo.firstName = bundle.getString(context.getString(R.string.first_name), "");
        signupInfo.lastName = bundle.getString(context.getString(R.string.last_name), "");
        signupInfo.city = bundle.getString(context.getString(R.string.city), "");
        signupInfo.address = bundle.getString(context.getString(R.string.street_address), "");
        signupInfo.zip = bundle.getString(context.getString(R.string.zip), "");
        signupInfo.bio = bundle.getString(context.getString(R.string.bio), "");
        signupInfo.email = bundle.getString(context.getString(R.string.email), "");
        signupInfo.password = bundle.getString(context.getString(R.string.password), "");
        signupInfo.age = bundle.getInt(context.getString(R.string.age), 0);
        signupInfo.gender = bundle.getString(context.getString(R.string.gender), "");
        signupInfo.isMentor = bundle.getBoolean(context.getString(R.string.mentor), false);

        signupInfo.interests = bundle.getStringArrayList(context.getString(R.string.interests_title));
        if (signupInfo.interests == null) {
            signupInfo.interests = new ArrayList<>();
        }

        return signupInfo;

    }

    public static Bundle writeToBundle(SignupInfo signupInfo, Context context) {
        Bundle bundle = new Bundle();

        // Put all the info in the bundle
        bundle.putInt(context.getString(R.string.age), signupInfo.age);
        bundle.putString(context.getString(R.string.first_name), signupInfo.firstName);
        bundle.putString(context.getString(R.string.last_name), signupInfo.lastName);
        bundle.putString(context.getString(R.string.email), signupInfo.email);
        bundle.putString(context.getString(R.string.city), signupInfo.city);
        bundle.putString(context.getString(R.string.street_address), signupInfo.address);
        bundle.putString(context.getString(R.string.zip), signupInfo.zip);
        bundle.putString(context.getString(R.string.bio), signupInfo.bio);
        bundle.putString(context.getString(R.string.password), signupInfo.password);
        bundle.putString(context.getString(R.string.gender), signupInfo.gender);
        bundle.putBoolean(context.getString(R.string.mentor), signupInfo.isMentor);
        bundle.putStringArrayList(context.getString(R.string.interests_title), signupInfo.interests);

        // return the bundle
        return bundle;
    }

}
