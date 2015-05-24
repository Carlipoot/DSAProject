package com.group6.entities;

public class Prison {

    public int prisonID;
    public int phoneNumber;
    public String streetAddress;
    public int postcode;
    public String city;
    public int capacity;
    String openHour;
    String closeHour;

    public Prison() {

    }

    public Prison(int prisonID, int phoneNumber, String streetAddress, int postcode, String city, int capacity, String openHour, String closeHour) {
        this.prisonID = prisonID;
        this.phoneNumber = phoneNumber;
        this.streetAddress = streetAddress;
        this.postcode = postcode;
        this.city = city;
        this.capacity = capacity;
        this.openHour = openHour;
        this.closeHour = closeHour;
    }

}
