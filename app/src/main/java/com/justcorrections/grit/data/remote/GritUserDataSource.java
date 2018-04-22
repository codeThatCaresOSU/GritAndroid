package com.justcorrections.grit.data.remote;

import com.justcorrections.grit.data.DatabaseHelper.DatabasePath;
import com.justcorrections.grit.data.model.Category;
import com.justcorrections.grit.data.model.GritUser;

/**
 * Created by Andrew Davis on 4/21/18.
 */

public class GritUserDataSource extends FirebaseDataSource<GritUser> {

    private static GritUserDataSource INSTANCE;

    public static GritUserDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GritUserDataSource();
        }
        return INSTANCE;
    }

    private GritUserDataSource() {
        super(DatabasePath.Users, GritUser.class);
    }

}

