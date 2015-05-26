package com.group6.entities;

import com.group6.ErrorForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public Boolean select(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<IEntity> selectAll(Connection connection) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            ArrayList<IEntity> entities = new ArrayList<IEntity>();

            statement = connection.prepareStatement("SELECT * FROM Buildings, Prisons WHERE BuildingID = PrisonID ORDER BY City");
            resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                Prison prison = new Prison();
                prison.set(resultSet);
                entities.add(prison);
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
        if ( postcode < 0 ) {
            new ErrorForm("Postcode cannot be negative");
            return false;
        } else if ( capacity <= 0 ) {
            new ErrorForm("Cannot have negative capacity");
            return false;
        }

        PreparedStatement statement = null;
        Boolean returnValue = false;

        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement("UPDATE Prisons SET Capacity = ?, OpenHours = ?, CloseHours = ? WHERE PrisonID = ?");
            statement.setInt(1, capacity);
            statement.setString(2, openHour);
            statement.setString(3, closeHour);
            statement.setInt(4, prisonID);

            returnValue = statement.executeUpdate() > 0;

            statement.close();

            statement = connection.prepareStatement("UPDATE Buildings SET PhoneNumber = ?, StreetAddress = ?, Postcode = ?, City = ? WHERE BuildingID = ?");
            statement.setLong(1, phoneNumber);
            statement.setString(2, streetAddress);
            statement.setInt(3, postcode);
            statement.setString(4, city);
            statement.setInt(5, prisonID);

            returnValue = returnValue && statement.executeUpdate() > 0;

            if ( !returnValue ) {
                new ErrorForm("Could not update data");
                connection.rollback();
            } else {
                connection.commit();
            }

            statement.close();

            return returnValue;
        } finally {
            statement.close();
        }
    }

    @Override
    public Boolean delete(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public void set(ResultSet resultSet) throws SQLException {
        prisonID = resultSet.getInt("PrisonID");
        phoneNumber = resultSet.getLong("PhoneNumber");
        streetAddress = resultSet.getString("StreetAddress");
        postcode = resultSet.getInt("Postcode");
        city = resultSet.getString("City");
        capacity = resultSet.getInt("Capacity");
        openHour = resultSet.getString("OpenHours");
        closeHour = resultSet.getString("CloseHours");
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
