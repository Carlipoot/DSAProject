package com.group6.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Officer implements IEntity {

    public int officerID;
    public String name;

    public Officer() {

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

            statement = connection.prepareStatement("SELECT * FROM Officers");

            resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                Officer officer = new Officer();
                officer.set(resultSet);
                entities.add(officer);
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
        officerID = resultSet.getInt("OfficerID");
        name = resultSet.getString("Name");
    }

    @Override
    public String toString() {
        return officerID + ": " + name;
    }
}
