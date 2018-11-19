package com.justcorrections.grit.data.model;

/**
 * Created by ianwillis on 3/7/18.
 */

public class FirebaseDataModel {

    private String id;

    FirebaseDataModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof FirebaseDataModel && ((FirebaseDataModel) other).id == this.id;
    }

}
