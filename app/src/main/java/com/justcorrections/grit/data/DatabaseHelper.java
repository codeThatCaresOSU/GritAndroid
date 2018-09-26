package com.justcorrections.grit.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Gus on 10/2/2017.
 */

public class DatabaseHelper {

    private static FirebaseDatabase database;

    static {
        database = FirebaseDatabase.getInstance();
    }

    public static DatabaseReference getReference(DatabasePath path) {
        return database.getReference(path.toString());
    }

    public enum DatabasePath {

        OhioData("MapData/OhioData"),
        Categories("MapData/Categories"),
        Mentees("Mentees"),
        Users("UsersPath"),
        TEST("Test-users");

        private final String path;

        DatabasePath(String path) {
            this.path = path;
        }

        public String toString() {
            return path;
        }
    }
}
