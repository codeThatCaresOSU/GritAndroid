package com.justcorrections.grit.data;

/**
 * Created by ianwillis on 11/16/17.
 */

public enum DatabasePath {

    OhioData("MapData/OhioData"),
    Categories("MapData/Categories");

    private final String path;

    private DatabasePath(String path) {
        this.path = path;
    }

    public String toString() {
        return path;
    }
}
