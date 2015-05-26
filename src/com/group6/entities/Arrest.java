package com.group6.entities;

import com.group6.ErrorForm;

import java.sql.*;
import java.util.ArrayList;

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
    public Boolean select(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<IEntity> selectAll(Connection connection) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            ArrayList<IEntity> entities = new ArrayList<IEntity>();

            statement = connection.prepareStatement("SELECT * FROM Arrests WHERE PersonID = ? ORDER BY \"Date\"");
            statement.setInt(1, personID);

            resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                Arrest arrest = new Arrest();
                arrest.set(resultSet);
                entities.add(arrest);
            }

            resultSet.close();
            statement.close();

            return entities;
        } finally {
            resultSet.close();
            statement.close();
        }
    }

    @Override
    public Boolean insert(Connection connection) throws SQLException {
        PreparedStatement statement = null;
        Boolean returnValue = false;

        try {
            statement = connection.prepareStatement("INSERT INTO Arrests VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, personID);

            returnValue = statement.execute();

            statement.close();

            return returnValue;
        } finally {
            statement.close();
        }
    }

    @Override
    public Boolean update(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public Boolean delete(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public void set(ResultSet resultSet) throws SQLException {
        officerID = resultSet.getInt("OfficerID");
        personID = resultSet.getInt("PersonID");
        date = resultSet.getDate("Date");
        postcode = resultSet.getInt("Postcode");
        evidence = resultSet.getString("Evidence");
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
