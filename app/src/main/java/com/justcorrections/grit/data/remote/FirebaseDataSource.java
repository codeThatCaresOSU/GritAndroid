package com.justcorrections.grit.data.remote;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.justcorrections.grit.data.DatabaseHelper;
import com.justcorrections.grit.data.DatabaseHelper.DatabasePath;
import com.justcorrections.grit.data.model.FirebaseDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ianwillis on 3/7/18.
 */

public abstract class FirebaseDataSource<T extends FirebaseDataModel> {

    private DatabaseReference data;
    private List<T> dataCache;
    private Class<T> dataType;

    FirebaseDataSource(DatabasePath path, Class<T> dataType) {
        data = DatabaseHelper.getReference(path);
        dataCache = null;
        this.dataType = dataType;
    }

    public void destroyCache() {
        dataCache = null;
    }

    public void getItems(@NonNull final GetItemsCallback<T> callback) {
        if (dataCache != null) {
            callback.onItemsLoaded(dataCache);
        } else {
            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<T> items = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        T item = child.getValue(dataType);
                        item.setId(child.getKey());
                        items.add(item);
                    }
                    dataCache = items;
                    callback.onItemsLoaded(items);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("ian c");

                    callback.onDataNotAvailable();
                }
            });
        }
    }

    public void getItem(final String id, @NonNull final GetItemCallback<T> callback) {
        if (dataCache != null) {
            for (T item : dataCache) {
                if (item.getId().equals(id)) {
                    callback.onItemLoaded(item);
                    return;
                }
            }
            callback.onItemLoaded(null);
        } else {
            getItems(new GetItemsCallback<T>() {
                @Override
                public void onItemsLoaded(List<T> items) {
                    for (T item : items) {
                        if (item.getId().equals(id)) {
                            callback.onItemLoaded(item);
                            return;
                        }
                    }
                    callback.onItemLoaded(null);
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });
        }
    }

    public void addItem(T item) {
        data.push().setValue(item);
    }

    public void updateItem(T item, String id) {
        DatabaseReference object = data.child(id);
        object.setValue(item);
    }

    public interface GetItemsCallback<T> {
        void onItemsLoaded(List<T> items);

        void onDataNotAvailable();
    }

    public interface GetItemCallback<T> {
        void onItemLoaded(T item);

        void onDataNotAvailable();
    }


}
