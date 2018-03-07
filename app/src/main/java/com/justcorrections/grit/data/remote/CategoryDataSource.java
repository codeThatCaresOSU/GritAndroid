package com.justcorrections.grit.data.remote;

import com.justcorrections.grit.data.DatabaseHelper.DatabasePath;
import com.justcorrections.grit.data.model.Category;

/**
 * Created by ianwillis on 11/27/17.
 */

public class CategoryDataSource extends FirebaseDataSource<Category> {

    private static CategoryDataSource INSTANCE;

    public static CategoryDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryDataSource();
        }
        return INSTANCE;
    }

    private CategoryDataSource() {
        super(DatabasePath.Categories, Category.class);
    }

}

