package com.justcorrections.grit.auth;

import com.google.firebase.auth.FirebaseUser;

import java.security.PrivateKey;

/**
 * Created by ianwillis on 2/21/18.
 */

public class GritUser {

    private static GritUser INSTANCE;

    private FirebaseUser user;
    private GritUserType userType;

    static GritUser login(FirebaseUser user) {
        // need to correctly find type
        GritUserType userType = GritUserType.MENTEE;
        INSTANCE = new GritUser(user, userType);
        return INSTANCE;
    }

    private GritUser(FirebaseUser user, GritUserType userType) {
        this.user = user;
        this.userType = userType;
    }


    public String getUid() {
        user.getUid();
    }

    public GritUserType getUserType() {
        return userType;
    }

}
