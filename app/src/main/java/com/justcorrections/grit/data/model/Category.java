package com.justcorrections.grit.data.model;

/**
 * Created by ianwillis on 3/7/18.
 */

public class Category extends FirebaseDataModel {

    private String color;
    private String name;

    public Category() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
