package com.justcorrections.grit.data.category;

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
 * Created by ianwillis on 11/27/17.
 */

public class CategoryDataSource {

    private static CategoryDataSource INSTANCE;

    private DatabaseReference data;
    private List<Category> categoryCache;

    public static CategoryDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryDataSource();
        }
        return INSTANCE;
    }

    private CategoryDataSource() {
        data = DatabaseHelper.getReference(DatabasePath.Categories);
        categoryCache = null;
    }

    public void destroyCache() {
        categoryCache = null;
    }

    public void getCategories(@NonNull final CategoryDataSource.GetCategoriesCallback callback) {
        if (categoryCache != null) {
            callback.onCategoriesLoaded(categoryCache);
        } else {
            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Category> categories = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Category c = child.getValue(Category.class);
                        categories.add(c);
                    }
                    categoryCache = categories;
                    callback.onCategoriesLoaded(categories);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onDataNotAvailable();
                }
            });
        }
    }

    public interface GetCategoriesCallback {

        void onCategoriesLoaded(List<Category> categories);

        void onDataNotAvailable();
    }
}

