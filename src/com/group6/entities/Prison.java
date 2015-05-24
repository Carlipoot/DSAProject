package com.group6.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Prison implements IEntity {

    public int prisonID = -1;
    public long phoneNumber;
    public String streetAddress;
    public int postcode;
    public String city;
    public int capacity;
    public String openHour;
    public String closeHour;

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

    @Override
    public String select() {
        return "SELECT * FROM Buildings, Prisons WHERE BuildingID = PrisonID";
    }

    @Override
    public String selectAll() {
        return "SELECT * FROM Buildings, Prisons WHERE BuildingID = PrisonID ORDER BY PrisonID";
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
            prisonID = resultSet.getInt("PrisonID");
            phoneNumber = resultSet.getLong("PhoneNumber");
            streetAddress = resultSet.getString("StreetAddress");
            postcode = resultSet.getInt("Postcode");
            city = resultSet.getString("City");
            capacity = resultSet.getInt("Capacity");
            openHour = resultSet.getString("OpenHours");
            closeHour = resultSet.getString("CloseHours");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "" + prisonID + "\n" +
                "" + phoneNumber + "\n" +
                "" + streetAddress + "\n" +
                "" + postcode + "\n" +
                "" + city + "\n" +
                "" + capacity + "\n" +
                "" + openHour + "\n" +
                "" + closeHour + "\n";
    }

}
