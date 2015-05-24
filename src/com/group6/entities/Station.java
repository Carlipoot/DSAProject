package com.group6.entities;

public class Station {

    public int stationID;
    public int phoneNumber;
    public String streetAddress;
    public int postcode;
    public String city;
    public int chiefID;
    public float radioFrequency;

    public Station() {

    }

    public Station(int stationID, int phoneNumber, String streetAddress, int postcode, String city, int chiefID, float radioFrequency) {
        this.stationID = stationID;
        this.phoneNumber = phoneNumber;
        this.streetAddress = streetAddress;
        this.postcode = postcode;
        this.city = city;
        this.chiefID = chiefID;
        this.radioFrequency = radioFrequency;
    }

}
