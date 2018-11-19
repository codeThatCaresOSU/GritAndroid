package com.justcorrections.grit.auth;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ianwillis on 2/21/18.
 */

public class GritUser {

    private FirebaseUser user;

    GritUser(FirebaseUser user) {
        this.user = user;
    }

    public String getUid() {
        return user.getUid();
    }

}
