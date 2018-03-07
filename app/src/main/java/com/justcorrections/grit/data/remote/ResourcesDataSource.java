package com.justcorrections.grit.data.remote;

import com.justcorrections.grit.data.DatabaseHelper.DatabasePath;
import com.justcorrections.grit.data.model.Resource;

/**
 * Created by ianwillis on 11/19/17.
 */

public class ResourcesDataSource extends FirebaseDataSource<Resource> {

    private static ResourcesDataSource INSTANCE;

    public static ResourcesDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ResourcesDataSource();
        }
        return INSTANCE;
    }

    private ResourcesDataSource() {
        super(DatabasePath.OhioData, Resource.class);
    }

}
