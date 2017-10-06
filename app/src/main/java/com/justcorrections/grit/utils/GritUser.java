package com.justcorrections.grit.utils;

/**
 * Created by Gus on 10/2/2017.
 */

public class GritUser {

    private String id;
    private String firstName;
    private String lastName;
    private String age;
    // categories?

    public GritUser() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
