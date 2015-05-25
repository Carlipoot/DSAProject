package com.group6.entities;

import com.group6.ErrorForm;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Station implements IEntity {

    public int stationID = -1;
    public long phoneNumber;
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

    @Override
    public String select() {
        return "SELECT * FROM Buildings, Stations WHERE BuildingID = StationID";
    }

    @Override
    public String selectAll() {
        return "SELECT * FROM Buildings, Stations WHERE BuildingID = StationID ORDER BY StationID";
    }

    @Override
    public String insert() {
        return null;
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }

    @Override
    public void set(ResultSet resultSet) {
        try {
            stationID = resultSet.getInt("StationID");
            phoneNumber = resultSet.getLong("PhoneNumber");
            streetAddress = resultSet.getString("StreetAddress");
            postcode = resultSet.getInt("Postcode");
            city = resultSet.getString("City");
            chiefID = resultSet.getInt("ChiefID");
            radioFrequency = resultSet.getInt("RadioFrequency");
        } catch (SQLException se) {
            new ErrorForm("Could not get data");
            se.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "" + stationID + "\n" +
                "" + phoneNumber + "\n" +
                "" + streetAddress + "\n" +
                "" + postcode + "\n" +
                "" + city + "\n" +
                "" + chiefID + "\n" +
                "" + radioFrequency + "\n";
    }

}
