package com.group6.entities;

import com.group6.ErrorForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public Boolean select(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<IEntity> selectAll(Connection connection) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            ArrayList<IEntity> entities = new ArrayList<IEntity>();

            statement = connection.prepareStatement("SELECT * FROM Buildings, Stations WHERE BuildingID = StationID ORDER BY City");
            resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                Station station = new Station();
                station.set(resultSet);
                entities.add(station);
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
        } else if ( radioFrequency < 0 ) {
            new ErrorForm("Cannot have negative frequency");
            return false;
        }

        PreparedStatement statement = null;
        Boolean returnValue = false;

        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement("UPDATE Stations SET ChiefID = ?, RadioFrequency = ? WHERE StationID = ?");
            statement.setInt(1, chiefID);
            statement.setFloat(2, radioFrequency);
            statement.setInt(3, stationID);

            returnValue = statement.executeUpdate() > 0;

            statement.close();

            statement = connection.prepareStatement("UPDATE Buildings SET PhoneNumber = ?, StreetAddress = ?, Postcode = ?, City = ? WHERE BuildingID = ?");
            statement.setLong(1, phoneNumber);
            statement.setString(2, streetAddress);
            statement.setInt(3, postcode);
            statement.setString(4, city);
            statement.setInt(5, stationID);

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
        stationID = resultSet.getInt("StationID");
        phoneNumber = resultSet.getLong("PhoneNumber");
        streetAddress = resultSet.getString("StreetAddress");
        postcode = resultSet.getInt("Postcode");
        city = resultSet.getString("City");
        chiefID = resultSet.getInt("ChiefID");
        radioFrequency = resultSet.getInt("RadioFrequency");
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
