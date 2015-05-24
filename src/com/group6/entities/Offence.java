package com.group6.entities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Offence implements IEntity {

    public int offenceID;
    public int personID;
    public Date date;
    public int postcode;
    public String description;

    public Offence() {

    }

    public Offence(int offenceID, int personID, Date date, int postcode, String description) {
        this.offenceID = offenceID;
        this.personID = personID;
        this.date = date;
        this.postcode = postcode;
        this.description = description;
    }

    @Override
    public String select() {
        return "SELECT * FROM Offences";
    }

    @Override
    public String selectAll() {
        return "SELECT * FROM Offences ORDER BY OffenceID";
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
            offenceID = resultSet.getInt("OffenceID");
            personID = resultSet.getInt("PersonID");
            date = resultSet.getDate("Date");
            postcode = resultSet.getInt("Postcode");
            description = resultSet.getString("Description");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "" + offenceID + "\n" +
                "" + personID + "\n" +
                "" + date + "\n" +
                "" + postcode + "\n" +
                "" + description + "\n";
    }

}
