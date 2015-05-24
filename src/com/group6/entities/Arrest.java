package com.group6.entities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Arrest implements IEntity {

    public int officerID = -1;
    public int personID = -1;
    public Date date;
    public int postcode;
    public String evidence;

    public Arrest() {

    }

    public Arrest(int officerID, int personID, int postcode, Date date, String evidence) {
        this.officerID = officerID;
        this.personID = personID;
        this.date = date;
        this.postcode = postcode;
        this.evidence = evidence;
    }

    @Override
    public String select() {
        if ( personID >= 0 )
            return "SELECT * FROM Arrests WHERE PersonID = " + personID;
        else
            return null;
    }

    @Override
    public String selectAll() {
        if ( personID >= 0 )
            return "SELECT * FROM Arrests WHERE PersonID = " + personID;
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
            officerID = resultSet.getInt("OfficerID");
            personID = resultSet.getInt("PersonID");
            date = resultSet.getDate("Date");
            postcode = resultSet.getInt("Postcode");
            evidence = resultSet.getString("Evidence");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "" + officerID + "\n" +
                "" + personID + "\n" +
                "" + date + "\n" +
                "" + postcode + "\n" +
                "" + evidence + "\n";
    }

}
