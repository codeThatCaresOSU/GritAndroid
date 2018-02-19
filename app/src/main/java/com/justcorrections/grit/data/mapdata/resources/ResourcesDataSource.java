package com.justcorrections.grit.data.mapdata.resources;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.justcorrections.grit.data.DatabaseHelper;
import com.justcorrections.grit.data.DatabaseHelper.DatabasePath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ianwillis on 11/19/17.
 */

public class ResourcesDataSource {

    private static ResourcesDataSource INSTANCE;

    private DatabaseReference data;
    private List<Resource> resourceCache;

    public static ResourcesDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ResourcesDataSource();
        }
        return INSTANCE;
    }

    private ResourcesDataSource() {
        data = DatabaseHelper.getReference(DatabasePath.OhioData);
        resourceCache = null;
    }

    public void destroyCache() {
        resourceCache = null;
    }

    public void getResource(final int id, @NonNull final GetResourceCallback callback) {
        final String idString = Integer.toString(id);
        if (resourceCache != null) {
            for (Resource r : resourceCache) {
                if (r.getId().equals(idString)) {
                    callback.onResourceLoaded(r);
                    return;
                }
            }
            callback.onResourceLoaded(null);
        } else {
            getResources(new GetResourcesCallback() {
                @Override
                public void onResourcesLoaded(List<Resource> resources) {
                    for (Resource r : resources) {
                        if (r.getId().equals(idString)) {
                            callback.onResourceLoaded(r);
                            return;
                        }
                    }
                    callback.onResourceLoaded(null);
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });

        }
    }

    public void getResources(@NonNull final GetResourcesCallback callback) {
        if (resourceCache != null) {
            callback.onResourcesLoaded(resourceCache);
        } else {
            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Resource> resources = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Resource r = child.getValue(Resource.class);
                        r.setId(child.getKey());
                        resources.add(r);
                    }
                    resourceCache = new ArrayList<>(resources);
                    callback.onResourcesLoaded(resources);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onDataNotAvailable();
                }
            });
        }
    }

    public interface GetResourceCallback {

        void onResourceLoaded(Resource resource);

        void onDataNotAvailable();
    }

    public interface GetResourcesCallback {

        void onResourcesLoaded(List<Resource> resources);

        void onDataNotAvailable();
    }

}
