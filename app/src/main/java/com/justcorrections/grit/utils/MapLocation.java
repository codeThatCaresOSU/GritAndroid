package com.justcorrections.grit.utils;

/**
 * Created by Gus on 10/20/2017.
 */

public class MapLocation {
    private String category;
    private String city;
    private String name;
    private String phone;
    private String state;
    private String street;
    private String url;
    private String zip;

    public MapLocation() {

    }

    // add another constructor, as necessary


    // these getters are required for firebase
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // these getters are required for firebase
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // these getters are required for firebase
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // these getters are required for firebase
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // these getters are required for firebase
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // these getters are required for firebase
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    // these getters are required for firebase
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // these getters are required for firebase
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
