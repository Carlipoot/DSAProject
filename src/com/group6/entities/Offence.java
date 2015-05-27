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

            statement = connection.prepareStatement("SELECT * FROM Offences WHERE PersonID = ? ORDER BY \"DATE\"");
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
            if ( resultSet != null )
                resultSet.close();
            if ( statement != null )
                statement.close();
        }
    }

    @Override
    public Boolean insert(Connection connection) throws SQLException {
        if ( personID < 0 ) {
            new ErrorForm("Error with data keys");
            return false;
        } else if ( postcode < 0 ) {
            new ErrorForm("Postcode cannot be negative");
            return false;
        }

        PreparedStatement statement = null;
        Boolean returnValue = false;

        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement("INSERT INTO Offences (PersonID, \"DATE\", Postcode, Description) " +
                    "VALUES (?, ?, ?, ?)");
            statement.setInt(1, personID);
            statement.setDate(2, date);
            statement.setInt(3, postcode);
            statement.setString(4, description);

            returnValue = statement.executeUpdate() > 0;

            if ( !returnValue ) {
                new ErrorForm("Could not update data");
                connection.rollback();
            } else {
                connection.commit();
            }

            statement.close();

            return returnValue;
        } finally {
            if ( statement != null )
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
