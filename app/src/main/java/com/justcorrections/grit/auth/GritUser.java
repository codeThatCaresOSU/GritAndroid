package com.justcorrections.grit.auth;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ianwillis on 2/21/18.
 */

public class GritUser {

    private FirebaseUser user;
    private GritUserType gritUserType;

    GritUser(FirebaseUser user, GritUserType gritUserType) {
        this.user = user;
        this.gritUserType = gritUserType;
    }

    public String getUid() {
        return user.getUid();
    }


    public enum GritUserType {
        MENTOR, MENTEE
    }

}
