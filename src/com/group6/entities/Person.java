package com.group6.entities;

import com.group6.ErrorForm;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Person implements IEntity {

    public int personID = -1;
    public String name;
    public Date birthDate;
    public String streetAddress;
    public int postcode;
    public String city;
    public String gender;

    public Person() {

    }

    public Person(int personID, String name, Date birthDate, String streetAddress, int postcode, String city, String gender) {
        this.personID = personID;
        this.name = name;
        this.birthDate = birthDate;
        this.streetAddress = streetAddress;
        this.postcode = postcode;
        this.city = city;
        this.gender = gender;
    }

    @Override
    public String select() {
        if ( name != null && streetAddress != null )
            return "SELECT * FROM People WHERE Name LIKE '%" + name + "%' AND StreetAddress LIKE '%" + streetAddress + "%'";
        else
            return null;
    }

    @Override
    public String selectAll() {
        if ( name != null && streetAddress != null )
            return "SELECT * FROM People WHERE Name LIKE '%" + name + "%' AND StreetAddress LIKE '%" + streetAddress + "%'";
        else
            return null;
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
            personID = resultSet.getInt("PersonID");
            name = resultSet.getString("Name");
            birthDate = resultSet.getDate("BirthDate");
            streetAddress = resultSet.getString("StreetAddress");
            postcode = resultSet.getInt("Postcode");
            city = resultSet.getString("City");
            gender = resultSet.getString("Gender");
        } catch (SQLException se) {
            new ErrorForm("Could not get data");
            se.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "" + personID + "\n" +
                "" + name + "\n" +
                "" + birthDate + "\n" +
                "" + streetAddress + "\n" +
                "" + postcode + "\n" +
                "" + city + "\n" +
                "" + gender + "\n";
    }

}
