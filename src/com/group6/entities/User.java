package com.group6.entities;

import com.group6.ErrorForm;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User implements IEntity {

    public String username;
    public String password;
    public String type;

    public User() {

    }

    public User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    @Override
    public Boolean select(Connection connection) throws SQLException {
        if ( username == null || password == null )
            return false;

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Boolean returnValue = false;

        try {
            statement = connection.prepareStatement("SELECT * FROM Users WHERE Username = ? AND Password = ?");
            statement.setString(1, username);
            statement.setString(2, password);

            resultSet = statement.executeQuery();

            if ( resultSet.next() ) {
                this.set(resultSet);
                returnValue = true;
            }

            resultSet.close();
            statement.close();

            return returnValue;
        } finally {
            resultSet.close();
            statement.close();
        }
    }

    @Override
    public ArrayList<IEntity> selectAll(Connection connection) throws SQLException {
        return null;
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
        username = resultSet.getString("Username");
        password = resultSet.getString("Password");
        type = resultSet.getString("Type");
    }

}
