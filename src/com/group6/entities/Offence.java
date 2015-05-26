package com.group6.entities;

import com.group6.ErrorForm;

import java.sql.*;
import java.util.ArrayList;

public class Offence implements IEntity {

    public int offenceID = -1;
    public int personID = -1;
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


    public String select() {
        if ( personID >= 0 )
            return "SELECT * FROM Offences WHERE PersonID = " + personID + " ORDER BY \"Date\"";
        else
            return null;
    }


    public String selectAll() {
        if ( personID >= 0 )
            return "SELECT * FROM Offences WHERE PersonID = " + personID + " ORDER BY \"Date\"";
        else
            return null;
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

            statement = connection.prepareStatement("SELECT * FROM Offences WHERE PersonID = ? ORDER BY \"Date\"");
            statement.setInt(1, personID);

            resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                Offence offence = new Offence();
                offence.set(resultSet);
                entities.add(offence);
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
        return null;
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
        offenceID = resultSet.getInt("OffenceID");
        personID = resultSet.getInt("PersonID");
        date = resultSet.getDate("Date");
        postcode = resultSet.getInt("Postcode");
        description = resultSet.getString("Description");
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
